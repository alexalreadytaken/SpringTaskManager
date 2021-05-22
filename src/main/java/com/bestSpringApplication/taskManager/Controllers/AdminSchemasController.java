package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.Summary;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.servises.interfaces.*;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.BadRequestException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.InvalidRequestParamException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.TaskInWorkException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/schemas")
public class AdminController {

    private final String MASTER_SCHEMAS =                       "/master";
    private final String MASTER_FILES =                         "/master/files";
    private final String MASTER_FILES_ADD =                     "/master/files/add";
    private final String MASTER_SCHEMA_BY_ID =                  "/master/schema/{schemaId}";
    private final String SCHEMA_SUMMARY =                       "/master/schema/{schemaId}/summary";
    private final String ADD_MASTER_SCHEMA_TO_USER =            "/master/schema/{schemaId}/addTo/user/{userId}";

    private final String INTERACTIONS_WITH_USER_TASK =          "/user/{userId}/schema/{schemaId}/task/{taskId}";
    private final String OPEN_USER_TASK =                       "/user/{userId}/schema/{schemaId}/task/{taskId}/open";
    private final String REOPEN_USER_TASK =                     "/user/{userId}/schema/{schemaId}/task/{taskId}/reopen";

    private final String USER_SCHEMAS =                         "/user/{userId}";
    private final String SUMMARY_OF_USER_SCHEMA =               "/user/{userId}/schemas/{schemaId}/summary";
    private final String STATE_OF_USER_SCHEMA =                 "/user/{userId}/schema/{schemaId}/state";
    private final String OPENED_TASKS_OF_SCHEMA_FOR_USER =      "/user/{userId}/schema/{schemaId}/opened";

    @NonNull private final SchemasProvider schemasProvider;
    @NonNull private final StudyService usersStudyService;
    @NonNull private final SummaryProvider summaryProvider;
    @NonNull private final UserService userService;
    @NonNull private final StudyStateService studyStateService;

    @GetMapping(SUMMARY_OF_USER_SCHEMA)
    public Summary summaryOfUserSchema(@PathVariable String schemaId, @PathVariable String userId){
        log.trace("request for summary of user '{}' by schema '{}'",userId,schemaId);
        schemasProvider.validateSchemaExistsOrThrow(schemaId);
        userService.validateUserExistsOrThrow(userId);
        return summaryProvider.getUserSchemaSummary(schemaId,userId);
    }

    @GetMapping(STATE_OF_USER_SCHEMA)
    public List<UserTaskRelation> stateOfUserSchema(@PathVariable String userId, @PathVariable String schemaId){
        log.trace("request for state of schema '{}' by user '{}'",schemaId,userId);
        schemasProvider.validateSchemaExistsOrThrow(schemaId);
        userService.validateUserExistsOrThrow(userId);
        return summaryProvider.getUserTasksState(schemaId,userId);
    }

    @GetMapping(SCHEMA_SUMMARY)
    public List<Summary> schemaSummary(@PathVariable String schemaId){
        log.trace("request for schema '{}' summary of all users",schemaId);
        schemasProvider.validateSchemaExistsOrThrow(schemaId);
        return summaryProvider.getTasksSummaryBySchema(schemaId);
    }

    @GetMapping(OPEN_USER_TASK)
    @ResponseStatus(HttpStatus.OK)
    public void openTask(@PathVariable String schemaId,@PathVariable String taskId,@PathVariable String userId,
                         @RequestParam(name = "force",required = false,defaultValue = "false") String forceQuery){
        log.trace("request for open task '{}' of schema '{}' for user '{}'",taskId,schemaId,userId);
        schemasProvider.validateSchemaExistsOrThrow(schemaId);
        schemasProvider.validateTaskInSchemaExistsOrThrow(schemaId,taskId);
        userService.validateUserExistsOrThrow(userId);
        boolean forceOpen = Boolean.parseBoolean(forceQuery);
        if (forceOpen) {
            usersStudyService.forceStartTask(schemaId, userId, taskId);
        } else {
            usersStudyService.startTaskWithValidation(schemaId, userId, taskId);
        }
    }

    @GetMapping(REOPEN_USER_TASK)
    @ResponseStatus(HttpStatus.OK)
    public void reopenTask(@PathVariable String schemaId,
                           @PathVariable String taskId,
                           @PathVariable String userId){
        log.trace("request for reopen task '{}' of schema '{}' for user '{}'",taskId,schemaId,userId);
        schemasProvider.validateSchemaExistsOrThrow(schemaId);
        schemasProvider.validateTaskInSchemaExistsOrThrow(schemaId,taskId);
        userService.validateUserExistsOrThrow(userId);
        usersStudyService.reopenTask(schemaId, userId, taskId);
    }

    @GetMapping(INTERACTIONS_WITH_USER_TASK)
    @ResponseStatus(HttpStatus.OK)
    public void interactionsWithUserTask(@RequestParam(name = "setGrade",required = false) Optional<String> grade,
                                         @RequestParam(name = "setStatus",required = false) Optional<String> status,
                                         @PathVariable String schemaId,
                                         @PathVariable String userId,
                                         @PathVariable String taskId){
        log.trace("request for task '{}' interactions of schema '{}' for user '{}'",taskId,schemaId,userId);
        schemasProvider.validateSchemaExistsOrThrow(schemaId);
        schemasProvider.validateTaskInSchemaExistsOrThrow(schemaId,taskId);
        userService.validateUserExistsOrThrow(userId);
        if (!studyStateService.schemaOfUserExists(schemaId,userId))throw new BadRequestException("схема не назначена");
        grade.ifPresent(gr->{
            Grade grade1 = Grade.of(gr).orElseThrow(() ->
                    new InvalidRequestParamException(String.format("оценки со значением %s не существует", gr)));
            log.trace("set grade '{}' for task '{}' of schema '{}' for user '{}'",grade1,taskId,schemaId,userId);
            studyStateService.setGradeForUserTask(schemaId,userId,taskId,grade1);
        });
        status.ifPresent(st->{
            Status status1 = Status.of(st).orElseThrow(() ->
                    new InvalidRequestParamException(String.format("статуса со значением %s не существует", st)));
            log.trace("set status '{}' for task '{}' of schema '{}' for user '{}'",status1,taskId,schemaId,userId);
            studyStateService.setStatusForUserTask(schemaId,userId,taskId,status1);
        });
    }

    @GetMapping(OPENED_TASKS_OF_SCHEMA_FOR_USER)
    public List<AbstractTask> openedStudentTasks(@PathVariable String schemaId,
                                                 @PathVariable String userId){
        log.trace("request for opened tasks of schema '{}' for user '{}'",schemaId,userId);
        schemasProvider.validateSchemaExistsOrThrow(schemaId);
        userService.validateUserExistsOrThrow(userId);
        return usersStudyService.getOpenedUserTasksOfSchema(userId,schemaId);
    }

    @GetMapping(USER_SCHEMAS)
    public List<AbstractTask> userSchemas(@PathVariable String userId){
        log.trace("request for opened schemas for user '{}'",userId);
        userService.validateUserExistsOrThrow(userId);
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
        log.trace("request for schema '{}' information",schemaId);
        schemasProvider.validateSchemaExistsOrThrow(schemaId);
        return schemasProvider.getSchemaById(schemaId);
    }

    @GetMapping(ADD_MASTER_SCHEMA_TO_USER)
    @ResponseStatus(HttpStatus.OK)
    public void addSchemaToStudent(@PathVariable String schemaId, @PathVariable String userId){
        log.trace("request for adding schema '{}' for user '{}'",schemaId,userId);
        schemasProvider.validateSchemaExistsOrThrow(schemaId);
        userService.validateUserExistsOrThrow(userId);
        if (studyStateService.schemaOfUserExists(schemaId, userId))throw new TaskInWorkException("курс уже назначен");
        usersStudyService.setSchemaToUser(userId,schemaId);
    }

    @GetMapping(MASTER_SCHEMAS)
    public List<AbstractTask> masterSchemasOverview(){
        return schemasProvider.getSchemasRootTasks();
    }

    @GetMapping(MASTER_FILES)
    public List<String> schemasFileList() {
        return schemasProvider.schemasFilenamesList();
    }
}

