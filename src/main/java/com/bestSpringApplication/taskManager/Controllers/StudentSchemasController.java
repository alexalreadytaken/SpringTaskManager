package com.bestSpringApplication.taskManager.Controllers;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.User;
import com.bestSpringApplication.taskManager.servises.interfaces.StudyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/study")
@RestController
@Slf4j
@RequiredArgsConstructor
public class StudentSchemasController {

    @NonNull private final StudyService studyService;

    private final String OPENED_SCHEMAS =       "/schemas/opened";
    private final String OPENED_SCHEMAS_TASKS = "/schemas/{schemaId}/opened";

    @GetMapping(OPENED_SCHEMAS)
    public List<AbstractTask> openedSchemas(@AuthenticationPrincipal User user){
        String userId = String.valueOf(user.getId());
        return studyService.getUserSchemasRootTasks(userId);
    }

    @GetMapping(OPENED_SCHEMAS_TASKS)
    public List<AbstractTask> openedSchemasTasks(@PathVariable String schemaId, @AuthenticationPrincipal User user){
        String userId = String.valueOf(user.getId());
        return studyService.getOpenedUserTasksOfSchema(userId,schemaId);
    }

}
