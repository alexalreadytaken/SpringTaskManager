package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.Summary;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import com.bestSpringApplication.taskManager.servises.interfaces.SchemasProvider;
import com.bestSpringApplication.taskManager.servises.interfaces.StudyService;
import com.bestSpringApplication.taskManager.servises.interfaces.SummaryProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    private final String ADD_MASTER_SCHEMA_TO_USER =            "/master/{schemaId}/addTo/{userId}";

    private final String INTERACTIONS_WITH_USER_TASK =          "/user/{userId}/{schemaId}/{taskId}";

    private final String USER_SCHEMAS =                         "/user/{userId}";
    private final String SUMMARY_OF_USER_SCHEMA =               "/user/{userId}/{schemaId}/summary";
    private final String STATE_OF_USER_SCHEMA =                 "/user/{userId}/{schemaId}/state";
    private final String OPENED_TASKS_OF_SCHEMA_FOR_USER =      "/user/{userId}/{schemaId}/opened";

    @NonNull private final SchemasProvider schemasProvider;
    @NonNull private final StudyService usersStudyService;
    @NonNull private final SummaryProvider summaryProvider;

    @GetMapping(SUMMARY_OF_USER_SCHEMA)
    public Summary summaryOfUserSchema(@PathVariable String schemaId, @PathVariable String userId){
        return summaryProvider.getUserSchemaSummary(schemaId,userId);
    }

    @GetMapping(STATE_OF_USER_SCHEMA)
    public List<UserTaskRelation> stateOfUserSchema(@PathVariable String userId, @PathVariable String schemaId){
        return summaryProvider.getUserTasksState(schemaId,userId);
    }

    @GetMapping(SCHEMA_SUMMARY)
    public List<Summary> schemaSummary(@PathVariable String schemaId){
        return summaryProvider.getTasksSummaryBySchema(schemaId);
    }

    @GetMapping(INTERACTIONS_WITH_USER_TASK)
    @ResponseStatus(HttpStatus.OK)
    public void interactionsWithUserTask(@RequestParam(required = false,name = "force",defaultValue = "false") String forceOpenQuery,
                                         @RequestParam(name = "action")String actionQuery,
                                         @PathVariable String schemaId,
                                         @PathVariable String userId,
                                         @PathVariable String taskId){
        switch (actionQuery.toLowerCase()){
            case "open":
                boolean forceOpen = Boolean.parseBoolean(forceOpenQuery);
                if (forceOpen) {
                    usersStudyService.forceStartTask(schemaId, userId, taskId);
                } else {
                    usersStudyService.startTaskWithValidation(schemaId, userId, taskId);
                }
                break;
            case "reopen":
                usersStudyService.reopenTask(schemaId, userId, taskId);
                break;
        }
    }

    @GetMapping(OPENED_TASKS_OF_SCHEMA_FOR_USER)
    public List<AbstractTask> openedStudentTasks(@PathVariable String schemaId,
                                                 @PathVariable String userId){
        return usersStudyService.getOpenedUserTasksOfSchema(userId,schemaId);
    }

    @GetMapping(USER_SCHEMAS)
    public List<AbstractTask> userSchemas(@PathVariable String userId){
        return usersStudyService.getUserSchemasRootTasks(userId);
    }



    @PostMapping(MASTER_FILES_ADD)
    @ResponseStatus(HttpStatus.OK)
    public void newSchema(@RequestParam("file") MultipartFile file){
        log.trace("Receive file:'{}' trying parse", file.getOriginalFilename());
        schemasProvider.putAndSaveFile(file);
    }

    @GetMapping(MASTER_SCHEMA_BY_ID)
    public AbstractStudySchema masterSchemaById(@PathVariable String schemaId){
        schemasProvider.validateSchemaExistsOrThrow(schemaId);
        return schemasProvider.getSchemaById(schemaId);
    }

    @GetMapping(ADD_MASTER_SCHEMA_TO_USER)
    @ResponseStatus(HttpStatus.OK)
    public void addSchemaToStudent(@PathVariable String schemaId, @PathVariable String userId){
        usersStudyService.setSchemaToUser(userId,schemaId);
    }

    @GetMapping(MASTER_SCHEMAS)
    public List<AbstractTask> masterSchemasOverview(){
        return schemasProvider.getSchemasRootTasks();
    }

    @GetMapping(MASTER_FILES)
    public List<String> schemasFileList() {
        List<String> fileNames = schemasProvider.schemasFilenamesList();
        log.trace("Request for master schemas files, return={}",fileNames);
        return fileNames;
    }
}
