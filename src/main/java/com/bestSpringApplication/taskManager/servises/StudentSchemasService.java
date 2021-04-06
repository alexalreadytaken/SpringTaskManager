package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.ContentNotFoundException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.TaskClosedException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentSchemasService {

    @NonNull private final MasterSchemasService masterSchemasService;
    @NonNull private final UserService userService;
    @NonNull private final UserTaskRelationService utrService;

    public void setSchemaToStudent(String studentId,String schemaKey){
        userService.validateExistsAndContainsRole(studentId,Role.STUDENT);
        utrService.prepareFirstTasks(masterSchemasService.schemaByKey(schemaKey),studentId);
    }

    public void forceStartTask(String schemaKey, String studentId, String taskId){
        startTask(schemaKey, studentId, taskId);
    }

    public void startTaskWithValidation(String schemaKey, String studentId, String taskId){
        if (utrService.canStartTask(schemaKey, studentId, taskId)){
            startTask(schemaKey, studentId, taskId);
        }else {
            throw new TaskClosedException("Задание невозможно начать (REWRITE EX TEXT)");
        }
    }

    private void startTask(String schemaKey, String studentId, String taskId) {
        AbstractStudySchema schema = masterSchemasService.schemaByKey(schemaKey);
        AbstractTask task = masterSchemasService.taskByIdInSchema(taskId,schemaKey);
        utrService.prepareTask(schema,task,studentId);
    }

    public Map<String, AbstractStudySchema> getStudentSchemas(String studentId) {
        userService.validateExistsAndContainsRole(studentId,Role.STUDENT);
        List<String> allOpenedSchemasToStudent = utrService.getAllOpenedSchemasKeysToStudent(studentId);

        Map<String, AbstractStudySchema> schemas = allOpenedSchemasToStudent.stream()
                .map(masterSchemasService::schemaByKey)
                .collect(Collectors.toMap(AbstractStudySchema::getKey, Function.identity()));
        if (schemas.size() == 0) throw new ContentNotFoundException("Курсы не назначены");
        return schemas;
    }

    public AbstractStudySchema getStudentSchema(String studentId, String schemaKey){
        return Optional
                .ofNullable(getStudentSchemas(studentId).get(schemaKey))
                .orElseThrow(()->new ContentNotFoundException("Курс не назначен или не существует"));
    }

    public List<AbstractTask> studentSchemasRootTasks(String studentId){
        return getStudentSchemas(studentId)
                .values().stream()
                .map(AbstractStudySchema::getRootTask)
                .collect(Collectors.toList());
    }

    public AbstractTask specificTaskOfStudentSchema(String schemaKey, String studentId, String taskId){
        AbstractStudySchema schema = getStudentSchema(studentId, schemaKey);
        return Optional
                .ofNullable(schema.getTasksMap().get(taskId))
                .orElseThrow(() -> new ContentNotFoundException("Задание не найдено"));
    }

    public List<AbstractTask> openedStudentTasksOfSchema(String studentId, String schemaKey){
        List<String> tasksId = utrService.getOpenedTasksIdBySchemaOfStudent(studentId, schemaKey);
        Map<String, AbstractTask> tasksMap = masterSchemasService.schemaByKey(schemaKey).getTasksMap();

        return tasksId.stream()
                .map(tasksMap::get)
                .collect(Collectors.toList());
    }

    // TODO: 4/3/21 how ^_^
    public List<AbstractTask> allOpenedStudentTasks(String studentId){
        Collection<AbstractStudySchema> studentSchemas = getStudentSchemas(studentId).values();
        List<AbstractTask> tasks = new ArrayList<>();
        studentSchemas.forEach(schema->
                schema.getTasksMap()
                        .values()
                        .stream()
                        .filter(AbstractTask::isOpened)
                        .forEach(tasks::add));
        return tasks;
    }
}




