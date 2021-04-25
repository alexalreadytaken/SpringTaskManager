package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.enums.RelationType;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.repos.UserTaskRelationRepo;
import com.bestSpringApplication.taskManager.servises.interfaces.StudyStateService;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.ContentNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersStudyStateService implements StudyStateService {

    @NonNull private final UserTaskRelationRepo utrRepo;

    public void prepareSchema(AbstractStudySchema schema, String userId){
        log.trace("start prepare schema '{}' to student '{}'",schema.getId(),userId);
        schema.tasksStream()
                .filter(task -> !task.isTheme())
                .peek(task -> prepareTask(schema, task, userId))
                .filter(task -> firstCheckTask(schema, task))
                .peek(task->log.trace("task 'name:{},id:{}' validated successful",task.getName(),task.getId()))
                .forEach(task->openTask(schema.getId(),userId,task.getId()));
    }

    public void prepareTask(AbstractStudySchema schema,AbstractTask task,String userId){
        UserTaskRelation userTaskRelation = UserTaskRelation.builder()
                .schemaId(schema.getId())
                .status(Status.CLOSED)
                .taskId(task.getId())
                .userId(userId)
                .grade(Grade.ONE)
                .build();
        utrRepo.save(userTaskRelation);
    }

    public List<String> getCompletedTasksIdOfSchemaForUser(String schemaId, String userId) {
        return utrRepo.getCompletedTasksIdOfSchemaIdAndUserId(userId, schemaId);
    }

    public List<UserTaskRelation> getAllRelationsBySchemaId(String schemaId){
        return utrRepo.getAllBySchemaId(schemaId);
    }

    public List<UserTaskRelation> getSchemaStateByUserId(String userId,String schemaId){
        return utrRepo.getAllBySchemaIdAndUserId(schemaId,userId);
    }

    public void openTask(String schemaId, String userId, String taskId){
        utrRepo.setStatusForTask(schemaId, userId, taskId,Status.IN_WORK);
    }

    public boolean taskFinished(String schemaId, String userId, String taskId) {
        return utrRepo.existsByTaskIdAndSchemaIdAndUserIdAndStatus(taskId,schemaId,userId,Status.FINISHED);
    }

    public boolean taskContainsStatus(String schemaId, String userId, String taskId, Status status) {
        return utrRepo.existsByTaskIdAndSchemaIdAndUserIdAndStatus(taskId,schemaId,userId,status);
    }

    public boolean taskInWork(String schemaId, String userId, String taskId){
        return utrRepo.existsByTaskIdAndSchemaIdAndUserIdAndStatus(taskId,schemaId,userId,Status.IN_WORK);
    }

    public boolean taskExists(String schemaId, String userId, String taskId){
        return utrRepo.existsBySchemaIdAndUserIdAndTaskId(schemaId, userId, taskId);
    }

    public List<String> getOpenedSchemasIdOfUser(String userId){
        List<String> schemasId = utrRepo.getOpenedSchemasIdOfUser(userId);
        if (schemasId.size()==0)throw new ContentNotFoundException("Курсы не назначены");
        return schemasId;
    }

    public List<UserTaskRelation> getRelationsBySchemaIdAndTaskId(String schemaId,String taskId){
        return utrRepo.getAllBySchemaIdAndTaskId(schemaId, taskId);
    }

    public List<String> getOpenedTasksIdBySchemaOfUser(String userId, String schemaId){
        List<String> tasksId = utrRepo.getOpenedTasksIdOfSchemaIdAndUserId(userId, schemaId);
        if (tasksId.size()==0)throw new ContentNotFoundException("Нет открытых заданий");
        return tasksId;
    }

    private boolean firstCheckTask(AbstractStudySchema schema,AbstractTask task){
        List<DependencyWithRelationType> dependencies = schema.getDependenciesWithRelationType();
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();

        return dependencies.stream()
                .filter(dependency -> dependency.getId1().equals(task.getId()))
                .allMatch(dependency -> {
                    AbstractTask taskParent = tasksMap.get(dependency.getId0());
                    boolean parentHierarchicalAndTheme = taskParent.isTheme() &&
                            dependency.getRelationType() == RelationType.HIERARCHICAL;
                    boolean parentsOfParentValid = firstCheckTask(schema, taskParent);
                    return parentHierarchicalAndTheme&&parentsOfParentValid;
                });
    }
}