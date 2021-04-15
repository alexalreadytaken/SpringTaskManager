package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.GroupTaskSummary;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.TaskClosedException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersSchemasService {

    @NonNull private final MasterSchemasService masterSchemasService;
    @NonNull private final UserService userService;
    @NonNull private final UserTaskRelationService utrService;

    public void setSchemaToUser(String userId,String schemaId){
        log.trace("trying prepare schema '{}' to student '{}'",schemaId,userId);
        userService.validateExistsAndContainsRole(userId,Role.STUDENT);
        utrService.prepareSchema(masterSchemasService.getSchemaById(schemaId),userId);
    }

    public void forceStartTask(String schemaId, String userId, String taskId){
        startTask(schemaId, userId, taskId);
    }

    public void startTaskWithValidation(String schemaId, String userId, String taskId){
        if (utrService.canStartTask(schemaId, userId, taskId)){
            startTask(schemaId, userId, taskId);
        }else {
            throw new TaskClosedException("Задание невозможно начать (REWRITE EX TEXT)");
        }
    }

    private void startTask(String schemaId, String userId, String taskId) {
        log.trace("trying start task '{}' in schema '{}' for student '{}'",taskId,schemaId,userId);
        AbstractStudySchema schema = masterSchemasService.getSchemaById(schemaId);
        AbstractTask task = masterSchemasService.getTaskByIdInSchema(taskId,schemaId);
        utrService.prepareTask(schema,task,userId);
    }

    public Map<String, AbstractStudySchema> getUserSchemas(String userId) {
        userService.validateExistsAndContainsRole(userId,Role.STUDENT);
        List<String> allOpenedSchemasToUser = utrService.getAllOpenedSchemasIdToUser(userId);
        Map<String, AbstractStudySchema> schemasMap = allOpenedSchemasToUser.stream()
                .map(masterSchemasService::getSchemaById)
                .collect(toMap(AbstractStudySchema::getId, Function.identity()));
        log.trace("request for all schemas of student '{}',return = {} ",userId,schemasMap.keySet());
        return schemasMap;
    }

    public List<AbstractTask> getUserSchemasRootTasks(String userId){
        return getUserSchemas(userId)
                .values().stream()
                .map(AbstractStudySchema::getRootTask)
                .collect(toList());
    }

    public List<AbstractTask> getOpenedUserTasksOfSchema(String userId, String schemaId){
        List<String> tasksId = utrService.getOpenedTasksIdBySchemaOfUser(userId, schemaId);
        Map<String, AbstractTask> tasksMap = masterSchemasService.getSchemaById(schemaId).getTasksMap();
        return tasksId.stream()
                .map(tasksMap::get)
                .collect(toList());
    }

    // TODO: 4/15/21 somehow better
    public List<GroupTaskSummary> getAllUsersTasksSummary(String schemaId){
        List<UserTaskRelation> relations = utrService.getAllRelationsBySchemaId(schemaId);
        return relations.stream()
                .map(UserTaskRelation::getTaskId)
                .distinct()
                .map(taskId -> {
                    List<UserTaskRelation>relationsByTask = relations.stream()
                            .filter(utr->utr.getTaskId().equals(taskId))
                            .collect(toList());
                    long finishedUsersCount = relationsByTask.stream()
                            .filter(utr->utr.getStatus()==Status.FINISHED)
                            .count();
                    return new GroupTaskSummary(taskId,finishedUsersCount/(double)relationsByTask.size()*100);
                }).collect(toList());
    }

    public List<UserTaskRelation> getUserTasksSummary(String schemaId, String userId){
        return utrService.getSchemaStateByUserId(userId,schemaId);
    }

}




