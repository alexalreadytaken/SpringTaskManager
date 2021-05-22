package com.bestSpringApplication.taskManager.Controllers;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.User;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.servises.interfaces.SchemasProvider;
import com.bestSpringApplication.taskManager.servises.interfaces.StudyService;
import com.bestSpringApplication.taskManager.servises.interfaces.StudyStateService;
import com.bestSpringApplication.taskManager.servises.interfaces.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/study")
@RestController
@Slf4j
@RequiredArgsConstructor
public class StudentController {

    @NonNull private final StudyService studyService;
    @NonNull private final StudyStateService studyStateService;
    @NonNull private final UserService userService;
    @NonNull private final SchemasProvider schemasProvider;


    private final String OPENED_SCHEMAS =       "/schemas/opened";
    private final String OPENED_SCHEMAS_TASKS = "/schemas/schema/{schemaId}/opened";
    private final String FINISH_TASK =          "/schemas/schema/{schemaId}/task/{taskId}/finish";
    private final String START_TASK =           "/schemas/schema/{schemaId}/task/{taskId}/start";

    @GetMapping(START_TASK)
    @ResponseStatus(HttpStatus.OK)
    public void startTask(@AuthenticationPrincipal User user,@PathVariable String schemaId, @PathVariable String taskId){
        schemasProvider.validateSchemaExistsOrThrow(schemaId);
        schemasProvider.validateTaskInSchemaExistsOrThrow(schemaId,taskId);
        userService.validateUserExistsOrThrow(user.getStringId());
        studyService.startTaskWithValidation(schemaId, user.getStringId(),taskId);
    }

    @GetMapping(FINISH_TASK)
    @ResponseStatus(HttpStatus.OK)
    public void finishTask(@AuthenticationPrincipal User user,@PathVariable String schemaId, @PathVariable String taskId){
        schemasProvider.validateSchemaExistsOrThrow(schemaId);
        schemasProvider.validateTaskInSchemaExistsOrThrow(schemaId,taskId);
        userService.validateUserExistsOrThrow(user.getStringId());
        studyStateService.setStatusForUserTask(schemaId,user.getStringId(),taskId,Status.NOT_CONFIRMED);
    }

    @GetMapping(OPENED_SCHEMAS)
    public List<AbstractTask> openedSchemas(@AuthenticationPrincipal User user){
        userService.validateUserExistsOrThrow(user.getStringId());
        return studyService.getUserSchemasRootTasks(user.getStringId());
    }

    @GetMapping(OPENED_SCHEMAS_TASKS)
    public List<AbstractTask> openedSchemasTasks(@PathVariable String schemaId, @AuthenticationPrincipal User user){
        schemasProvider.validateSchemaExistsOrThrow(schemaId);
        userService.validateUserExistsOrThrow(user.getStringId());
        return studyService.getOpenedUserTasksOfSchema(user.getStringId(),schemaId);
    }

}

