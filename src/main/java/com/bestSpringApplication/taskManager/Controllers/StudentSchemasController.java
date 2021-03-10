package com.bestSpringApplication.taskManager.Controllers;

import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.user.User;
import com.bestSpringApplication.taskManager.servises.StudentSchemasService;
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

    @NonNull private final StudentSchemasService schemasService;

    private final String OPENED_SCHEMAS = "/schemas/opened";
    private final String OPENED_SCHEMAS_TASKS = "/schemas/{schemaKey}/opened";
    private final String ALL_OPENED_TASKS = "/tasks/opened";

    @GetMapping(OPENED_SCHEMAS)
    public List<AbstractTask> openedSchemas(@AuthenticationPrincipal User user){
        String studentId = String.valueOf(user.getId());
        return schemasService.studentSchemasOverview(studentId);
    }

    @GetMapping(OPENED_SCHEMAS_TASKS)
    public List<AbstractTask> openedSchemasTasks(@PathVariable String schemaKey, @AuthenticationPrincipal User user){
        String studentId = String.valueOf(user.getId());
        return schemasService.openedStudentTasks(studentId,schemaKey);
    }

    @GetMapping(ALL_OPENED_TASKS)
    public List<AbstractTask> allOpenedTasks(@AuthenticationPrincipal User user){
        String studentId = String.valueOf(user.getId());
        return schemasService.allOpenedStudentTasks(studentId);
    }

}
