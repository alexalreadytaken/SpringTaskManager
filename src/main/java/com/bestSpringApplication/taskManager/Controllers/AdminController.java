package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.Summary;
import com.bestSpringApplication.taskManager.models.entities.UserTaskState;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.servises.interfaces.*;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.BadRequestException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.InvalidRequestParamException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.TaskInWorkException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final String SCHEMA_SUMMARY =                       "/schema/{schemaId}/summary";
    private final String STATE_OF_TASK_IN_SCHEMA =              "/schema/{schemaId}/task/{taskId}/state";

    private final String ADD_SCHEMA_TO_GROUP =                  "/schema/{schemaId}/addTo/group/{groupId}";
    private final String ADD_SCHEMA_TO_USER =                   "/schema/{schemaId}/addTo/user/{userId}";

    private final String STATUS_VALUES_RU =                     "/status/ru";

    private final String UNCONFIRMED_TASKS =                    "/tasks/unconfirmed";

    private final String INTERACTIONS_WITH_USER_TASK =          "/user/{userId}/schema/{schemaId}/task/{taskId}";

    private final String USER_SCHEMAS =                         "/user/{userId}/schemas";
    private final String SUMMARY_OF_USER_SCHEMA =               "/user/{userId}/schema/{schemaId}/summary";
    private final String STATE_OF_USER_SCHEMA =                 "/user/{userId}/schema/{schemaId}/state";
    private final String OPENED_TASKS_OF_SCHEMA_FOR_USER =      "/user/{userId}/schema/{schemaId}/opened";

    @NonNull private final SchemasService schemasService;
    @NonNull private final StudyService usersStudyService;
    @NonNull private final SummaryProvider summaryProvider;
    @NonNull private final UserService userService;
    @NonNull private final StudyStateService studyStateService;
    @NonNull private final GroupService groupService;


    @GetMapping(UNCONFIRMED_TASKS)
    public List<UserTaskState> unconfirmedUsersTasks(){
        return studyStateService.getUnconfirmedTasks();
    }

    @GetMapping(SUMMARY_OF_USER_SCHEMA)
    public Summary summaryOfUserSchema(@PathVariable String schemaId, @PathVariable String userId){
        log.trace("request for summary of user '{}' by schema '{}'",userId,schemaId);
        schemasService.validateSchemaExistsOrThrow(schemaId);
        userService.validateUserExistsOrThrow(userId);
        return summaryProvider.getUserSchemaSummary(schemaId,userId);
    }

    @GetMapping(STATE_OF_USER_SCHEMA)
    public List<UserTaskState> stateOfUserSchema(@PathVariable String userId, @PathVariable String schemaId){
        log.trace("request for state of schema '{}' by user '{}'",schemaId,userId);
        schemasService.validateSchemaExistsOrThrow(schemaId);
        userService.validateUserExistsOrThrow(userId);
        return studyStateService.getSchemaStateByUserId(userId,schemaId);
    }

    @GetMapping(STATE_OF_TASK_IN_SCHEMA)
    public List<UserTaskState> stateOfTaskInSchema(@PathVariable String taskId, @PathVariable String schemaId){
        log.trace("request for state of task '{}' in schema '{}' ",taskId,schemaId);
        schemasService.validateSchemaExistsOrThrow(schemaId);
        schemasService.validateTaskInSchemaExistsOrThrow(schemaId,taskId);
        return studyStateService.getTaskStateInSchema(schemaId,taskId);
    }

    @GetMapping(SCHEMA_SUMMARY)
    public List<Summary> schemaSummary(@PathVariable String schemaId){
        log.trace("request for schema '{}' summary of all users",schemaId);
        schemasService.validateSchemaExistsOrThrow(schemaId);
        return summaryProvider.getTasksSummaryBySchema(schemaId);
    }

    @GetMapping(INTERACTIONS_WITH_USER_TASK)
    @ResponseStatus(HttpStatus.OK)
    public void interactionsWithUserTask(@RequestParam(name = "setPercentComplete",required = false) Optional<Double> percent,
                                         @RequestParam(name = "setStatus",required = false) Optional<String> status,
                                         @PathVariable String schemaId,
                                         @PathVariable String userId,
                                         @PathVariable String taskId){
        log.trace("request for task '{}' interactions of schema '{}' for user '{}'",taskId,schemaId,userId);
        schemasService.validateSchemaExistsOrThrow(schemaId);
        schemasService.validateTaskInSchemaExistsOrThrow(schemaId,taskId);
        userService.validateUserExistsOrThrow(userId);
        if (!studyStateService.schemaOfUserExists(schemaId,userId))throw new BadRequestException("схема не назначена");
        percent.ifPresent(p->{
            if (0>p||p>1)throw new InvalidRequestParamException("некоректное значение процента завершенности");
            log.trace("set percent complete '{}' for task '{}' of schema '{}' for user '{}'",p,taskId,schemaId,userId);
            studyStateService.setPercentCompleteForUserTask(schemaId,userId,taskId,p);
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
        schemasService.validateSchemaExistsOrThrow(schemaId);
        userService.validateUserExistsOrThrow(userId);
        return usersStudyService.getOpenedUserTasks(userId,schemaId);
    }

    @GetMapping(USER_SCHEMAS)
    public List<AbstractTask> userSchemas(@PathVariable String userId){
        log.trace("request for opened schemas for user '{}'",userId);
        userService.validateUserExistsOrThrow(userId);
        return usersStudyService.getUserSchemasRootTasks(userId);
    }

    @GetMapping(STATUS_VALUES_RU)
    public List<String> getRuStatusValues(){
        return Status.ruValues();
    }

    @GetMapping(ADD_SCHEMA_TO_GROUP)
    @ResponseStatus(HttpStatus.OK)
    public void addSchemaToGroup(@PathVariable String groupId, @PathVariable String schemaId){
        log.trace("request for adding schema '{}' for group '{}'",schemaId,groupId);
        schemasService.validateSchemaExistsOrThrow(schemaId);
        groupService.validateGroupExistsOrThrow(groupId);
        if (studyStateService.schemaOfGroupExists(groupId,schemaId)) {
            throw new TaskInWorkException("курс уже назначен некоторым студентам или всей группе");
        }
        usersStudyService.setSchemaToGroup(groupId, schemaId);
    }

    @GetMapping(ADD_SCHEMA_TO_USER)
    @ResponseStatus(HttpStatus.OK)
    public void addSchemaToStudent(@PathVariable String schemaId, @PathVariable String userId){
        log.trace("request for adding schema '{}' for user '{}'",schemaId,userId);
        schemasService.validateSchemaExistsOrThrow(schemaId);
        userService.validateUserExistsOrThrow(userId);
        if (studyStateService.schemaOfUserExists(schemaId, userId))throw new TaskInWorkException("курс уже назначен");
        usersStudyService.setSchemaToUser(userId,schemaId);
    }

}

