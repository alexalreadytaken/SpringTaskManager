package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.utils.exceptions.forClient.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.servises.MasterSchemasService;
import com.bestSpringApplication.taskManager.servises.StudentSchemasService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/schemas")
public class AdminSchemasController {

    private final String MASTER_SCHEMAS =                       "/master";
    private final String MASTER_FILES =                         "/master/files";
    private final String MASTER_FILES_ADD =                     "/master/files/add";
    private final String MASTER_SCHEMA_BY_ID =                  "/master/{schemaId}";
    private final String ADD_MASTER_SCHEMA_TO_STUDENT =         "/master/{schemaId}/addTo/{studentId}";

    private final String OPEN_TASK_FOR_STUDENT =                "/student/{studentId}/{schemaId}/{taskId}/open";

    private final String STUDENT_SCHEMAS =                      "/student/{studentId}";
    private final String OPENED_TASKS_OF_SCHEMAS_FOR_STUDENT =  "/student/{studentId}/opened";
    private final String OPENED_TASKS_OF_SCHEMA_FOR_STUDENT =   "/student/{studentId}/{schemaId}/opened";


    @NonNull private final MasterSchemasService masterSchemasService;
    @NonNull private final StudentSchemasService studentSchemasService;

    private static final Set<String> confirmedFileTypes =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    "xml","mrp","txt")));

    @GetMapping(OPEN_TASK_FOR_STUDENT)
    @ResponseStatus(HttpStatus.OK)
    public void openTaskForStudent(@RequestParam(required = false,name = "force",defaultValue = "false") String forceOpenQuery,
                                   @PathVariable String schemaId,
                                   @PathVariable String studentId,
                                   @PathVariable String taskId){
        boolean forceOpen = Boolean.parseBoolean(forceOpenQuery);
        if (forceOpen){
            studentSchemasService.forceStartTask(schemaId, studentId, taskId);
        }else{
            studentSchemasService.startTaskWithValidation(schemaId, studentId, taskId);
        }
    }

    @GetMapping(OPENED_TASKS_OF_SCHEMAS_FOR_STUDENT)
    public List<AbstractTask> allOpenedStudentTasks(@PathVariable String studentId){
        log.trace("Request for all opened student '{}' tasks",studentId);
        return studentSchemasService.allOpenedStudentTasks(studentId);
    }

    @GetMapping(OPENED_TASKS_OF_SCHEMA_FOR_STUDENT)
    public List<AbstractTask> openedStudentTasks(@PathVariable String schemaId,
                                                 @PathVariable String studentId){
        return studentSchemasService.openedStudentTasksOfSchema(studentId,schemaId);
    }

    @GetMapping(MASTER_SCHEMA_BY_ID)
    public AbstractStudySchema masterSchemaById(@PathVariable String schemaId){
        return masterSchemasService.schemaById(schemaId);
    }

    @GetMapping(ADD_MASTER_SCHEMA_TO_STUDENT)
    @ResponseStatus(HttpStatus.OK)
    public void addSchemaToStudent(@PathVariable String schemaId, @PathVariable String studentId, HttpServletRequest request){
        log.trace("new relation student to schema;schemaId = {},studentId = {} ",schemaId,studentId);
        studentSchemasService.setSchemaToStudent(studentId,schemaId);
    }

    @PostMapping(MASTER_FILES_ADD)
    @ResponseStatus(HttpStatus.OK)
    public void newSchema(@RequestParam("file") MultipartFile file){
        String[] fileNameAndType = Objects.requireNonNull(file.getOriginalFilename()).split("\\.", 2);
        log.trace("Receive file:{}", file.getOriginalFilename());
        if (confirmedFileTypes.contains(fileNameAndType[1])) {
            masterSchemasService.putAndSaveFile(file);
        } else {
            log.warn("unsupported file type sent,file:{}", file.getOriginalFilename());
            throw new IllegalFileFormatException(String.format("файл с расширением %s не поддерживается", fileNameAndType[1]));
        }
    }

    @GetMapping(STUDENT_SCHEMAS)
    public List<AbstractTask> studentSchemas(@PathVariable String studentId){
        return studentSchemasService.studentSchemasRootTasks(studentId);
    }

    @GetMapping(MASTER_SCHEMAS)
    public List<AbstractTask> masterSchemasOverview(){
        log.trace("Request for all master schemas");
        return masterSchemasService.schemasRootTasks();
    }

    @GetMapping(MASTER_FILES)
    public List<String> schemasFileList() {
        List<String> fileNames = masterSchemasService.schemasFileList();
        log.trace("Request for master schemas files, return={}",fileNames);
        return fileNames;
    }
}

