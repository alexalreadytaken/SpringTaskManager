package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.forClient.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.servises.MasterSchemasService;
import com.bestSpringApplication.taskManager.servises.StudentSchemasService;
import com.bestSpringApplication.taskManager.servises.UserTaskRelationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/schemas")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class SchemasController {

    private final String MASTER_SCHEMAS =                   "/master";
    private final String MASTER_FILES =                     "/master/files";
    private final String MASTER_FILES_ADD =                 "/master/files/add";
    private final String MASTER_SCHEMA_BY_KEY =             "/master/{schemaKey}";
    private final String ADD_MASTER_SCHEMA_TO_STUDENT =     "/master/{schemaKey}/addTo/{studentId}";

    private final String STUDENT_SCHEMAS =                  "/student/{studentId}";
    private final String OPEN_TASKS_OF_SCHEMAS_FOR_STUDENT= "/student/{studentId}/opened";
    private final String OPEN_TASKS_OF_SCHEMA_FOR_STUDENT = "/student/{studentId}/{schemaKey}/opened";
    private final String SPECIFIC_TASK_OF_STUDENT_SCHEME =  "/student/{studentId}/{schemaKey}/{taskId}";


    @NonNull private final MasterSchemasService masterSchemasService;
    @NonNull private final StudentSchemasService studentSchemasService;
    @NonNull private final UserTaskRelationService utrService;

    private static final Set<String> confirmedFileTypes =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    "xml","mrp","txt")));


    @GetMapping(SPECIFIC_TASK_OF_STUDENT_SCHEME)
    public AbstractTask specificTaskOfStudentSchema(@PathVariable String studentId, @PathVariable String taskId, @PathVariable String schemaKey){
        log.trace("Request for task '{}' in schema '{}' of student '{}'",taskId,schemaKey,studentId);
        return studentSchemasService.specificTaskOfStudentScheme(schemaKey,studentId,taskId);
    }

    @GetMapping(OPEN_TASKS_OF_SCHEMAS_FOR_STUDENT)
    public List<AbstractTask> allOpenedStudentTasks(@PathVariable String studentId){
        log.trace("Request for all opened student '{}' tasks",studentId);
        return studentSchemasService.allOpenedStudentTasks(studentId);
    }

    @GetMapping(OPEN_TASKS_OF_SCHEMA_FOR_STUDENT)
    public List<AbstractTask> openedStudentTasks(@PathVariable String schemaKey, @PathVariable String studentId){
        return studentSchemasService.openedStudentTasks(studentId,schemaKey);
    }

    @GetMapping(MASTER_SCHEMA_BY_KEY)
    public AbstractStudySchema masterSchemaByKey(@PathVariable String schemaKey){
        return masterSchemasService.schemaByKey(schemaKey);
    }

    @GetMapping(ADD_MASTER_SCHEMA_TO_STUDENT)
    @ResponseStatus(HttpStatus.OK)
    public void addSchemaToStudent(@PathVariable String schemaKey, @PathVariable String studentId){
        log.trace("new relation student to schema;schemaKey = {},studentId = {} ",schemaKey,studentId);
        studentSchemasService.setSchemaToStudent(studentId,schemaKey);
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

    // TODO: 2/28/2021 Json view
    @GetMapping(STUDENT_SCHEMAS)
    public List<AbstractTask> studentSchemas(@PathVariable String studentId){
        return studentSchemasService.studentSchemasOverview(studentId);
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

