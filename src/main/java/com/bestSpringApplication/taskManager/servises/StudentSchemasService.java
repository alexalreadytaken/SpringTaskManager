package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.TaskClosedException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentSchemasService {

    @NonNull private final MasterSchemasService masterSchemasService;
    @NonNull private final UserService userService;
    @NonNull private final UserTaskRelationService utrService;

    public void setSchemaToStudent(String studentId,String schemaId){
        log.trace("trying prepare schema '{}' to student '{}'",schemaId,studentId);
        userService.validateExistsAndContainsRole(studentId,Role.STUDENT);
        utrService.prepareSchema(masterSchemasService.schemaById(schemaId),studentId);
    }

    public void forceStartTask(String schemaId, String studentId, String taskId){
        startTask(schemaId, studentId, taskId);
    }

    public void startTaskWithValidation(String schemaId, String studentId, String taskId){
        if (utrService.canStartTask(schemaId, studentId, taskId)){
            startTask(schemaId, studentId, taskId);
        }else {
            throw new TaskClosedException("Задание невозможно начать (REWRITE EX TEXT)");
        }
    }

    private void startTask(String schemaId, String studentId, String taskId) {
        log.trace("trying start task '{}' in schema '{}' for student '{}'",taskId,schemaId,studentId);
        AbstractStudySchema schema = masterSchemasService.schemaById(schemaId);
        AbstractTask task = masterSchemasService.taskByIdInSchema(taskId,schemaId);
        utrService.prepareTask(schema,task,studentId);
    }

    public Map<String, AbstractStudySchema> getStudentSchemas(String studentId) {
        userService.validateExistsAndContainsRole(studentId,Role.STUDENT);
        List<String> allOpenedSchemasToStudent = utrService.getAllOpenedSchemasIdToStudent(studentId);
        Map<String, AbstractStudySchema> schemasMap = allOpenedSchemasToStudent.stream()
                .map(masterSchemasService::schemaById)
                .collect(Collectors.toMap(AbstractStudySchema::getId, Function.identity()));
        log.trace("request for all schemas of student '{}',return = {} ",studentId,schemasMap.keySet());
        return schemasMap;
    }

    public List<AbstractTask> studentSchemasRootTasks(String studentId){
        return getStudentSchemas(studentId)
                .values().stream()
                .map(AbstractStudySchema::getRootTask)
                .collect(Collectors.toList());
    }

    public List<AbstractTask> openedStudentTasksOfSchema(String studentId, String schemaId){
        List<String> tasksId = utrService.getOpenedTasksIdBySchemaOfStudent(studentId, schemaId);
        Map<String, AbstractTask> tasksMap = masterSchemasService.schemaById(schemaId).getTasksMap();

        return tasksId.stream()
                .map(tasksMap::get)
                .collect(Collectors.toList());
    }

}




