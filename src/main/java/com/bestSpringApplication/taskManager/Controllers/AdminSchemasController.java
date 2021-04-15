package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.models.classes.GroupTaskSummary;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.servises.MasterSchemasService;
import com.bestSpringApplication.taskManager.servises.UsersSchemasService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final String SCHEMA_SUMMARY =                       "/master/{schemaId}/summary";
    private final String ADD_MASTER_SCHEMA_TO_USER =            "/master/{schemaId}/addTo/{studentId}";

    private final String OPEN_TASK_FOR_USER =                   "/student/{studentId}/{schemaId}/{taskId}/open";

    private final String USER_SCHEMAS =                         "/student/{studentId}";
    private final String SUMMARY_OF_USER_SCHEMA =               "student/{studentId}/{schemaId}/summary";
    private final String OPENED_TASKS_OF_SCHEMA_FOR_USER =      "/student/{studentId}/{schemaId}/opened";


    @NonNull private final MasterSchemasService masterSchemasService;
    @NonNull private final UsersSchemasService usersSchemasService;

    private static final Set<String> confirmedFileTypes = Set.of("xml", "mrp", "txt");

    @GetMapping(SUMMARY_OF_USER_SCHEMA)
    public List<UserTaskRelation> summaryOfUserSchema(@PathVariable String studentId, @PathVariable String schemaId){
        return usersSchemasService.getUserTasksSummary(schemaId,studentId);
    }

    @GetMapping(OPEN_TASK_FOR_USER)
    @ResponseStatus(HttpStatus.OK)
    public void openTaskForStudent(@RequestParam(required = false,name = "force",defaultValue = "false") String forceOpenQuery,
                                   @PathVariable String schemaId,
                                   @PathVariable String studentId,
                                   @PathVariable String taskId){
        boolean forceOpen = Boolean.parseBoolean(forceOpenQuery);
        if (forceOpen){
            usersSchemasService.forceStartTask(schemaId, studentId, taskId);
        }else{
            usersSchemasService.startTaskWithValidation(schemaId, studentId, taskId);
        }
    }

    @GetMapping(OPENED_TASKS_OF_SCHEMA_FOR_USER)
    public List<AbstractTask> openedStudentTasks(@PathVariable String schemaId,
                                                 @PathVariable String studentId){
        return usersSchemasService.getOpenedUserTasksOfSchema(studentId,schemaId);
    }

    @GetMapping(USER_SCHEMAS)
    public List<AbstractTask> studentSchemas(@PathVariable String studentId){
        return usersSchemasService.getUserSchemasRootTasks(studentId);
    }

    @GetMapping(SCHEMA_SUMMARY)
    public List<GroupTaskSummary> schemaSummary(@PathVariable String schemaId){
        return usersSchemasService.getAllUsersTasksSummary(schemaId);
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

    @GetMapping(MASTER_SCHEMA_BY_ID)
    public AbstractStudySchema masterSchemaById(@PathVariable String schemaId){
        return masterSchemasService.getSchemaById(schemaId);
    }

    @GetMapping(ADD_MASTER_SCHEMA_TO_USER)
    @ResponseStatus(HttpStatus.OK)
    public void addSchemaToStudent(@PathVariable String schemaId, @PathVariable String studentId){
        usersSchemasService.setSchemaToUser(studentId,schemaId);
    }

    @GetMapping(MASTER_SCHEMAS)
    public List<AbstractTask> masterSchemasOverview(){
        return masterSchemasService.getSchemasRootTasks();
    }

    @GetMapping(MASTER_FILES)
    public List<String> schemasFileList() {
        List<String> fileNames = masterSchemasService.schemasFileList();
        log.trace("Request for master schemas files, return={}",fileNames);
        return fileNames;
    }
}

