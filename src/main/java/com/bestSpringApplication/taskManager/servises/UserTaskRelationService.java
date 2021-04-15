package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.enums.RelationType;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.models.interfaces.Dependency;
import com.bestSpringApplication.taskManager.repos.UserTaskRelationRepo;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.ContentNotFoundException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.TaskInWorkException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.TaskIsThemeException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTaskRelationService {

    @NonNull private final UserTaskRelationRepo utrRepo;
    @NonNull private final MasterSchemasService masterSchemasService;

    public boolean canStartTask(String schemaId, String userId, String taskId){
        AbstractStudySchema schema = masterSchemasService.getSchemaById(schemaId);
        AbstractTask task = masterSchemasService.getTaskByIdInSchema(taskId, schemaId);
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();
        List<DependencyWithRelationType> dependencies = schema.getDependencies();

        // TODO: 4/4/21 reopen
        if (existsBySchemaIdAndUserIdAndTaskId(schemaId,userId,taskId)){
            throw new TaskInWorkException("Задание уже начато");
        }else if (task.isTheme()){
            throw new TaskIsThemeException("Тему начать невозможно!");
        }

        Set<String> finishedTasksId = new HashSet<>(getCompletedTasksIdOfSchemaForUser(schemaId, userId));

        boolean parentsOnlyHierarchical = dependencies.stream()
                .filter(dep->dep.getId1().equals(taskId))
                .map(DependencyWithRelationType::getRelationType)
                .allMatch(RelationType.HIERARCHICAL::isInstance);

        boolean taskNotSuccessor;

        if (parentsOnlyHierarchical){
            taskNotSuccessor = deepCheck(taskId, tasksMap, dependencies, finishedTasksId);
        }else {
            taskNotSuccessor = defaultCheck(taskId, dependencies, finishedTasksId);
        }
        return taskNotSuccessor;
    }

    public boolean firstCheckTask(AbstractStudySchema schema,AbstractTask task){
        List<DependencyWithRelationType> dependencies = schema.getDependencies();
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

    public void prepareSchema(AbstractStudySchema schema, String userId){
        log.trace("start prepare schema '{}' to student '{}'",schema.getId(),userId);
        schema.tasksStream()
                .filter(task -> !task.isTheme())
                .filter(task -> firstCheckTask(schema,task))
                .peek(task->log.trace("task 'name:{},id:{}' validated successful",task.getName(),task.getId()))
                .forEach(task->prepareTask(schema,task,userId));
    }

    public void prepareTask(AbstractStudySchema schema,AbstractTask task,String userId){
        UserTaskRelation userTaskRelation = UserTaskRelation.builder()
                .schemaId(schema.getId())
                .status(Status.IN_WORK)
                .taskId(task.getId())
                .userId(userId)
                .grade(Grade.ONE)
                .build();
        log.trace("save {}",userTaskRelation);
        utrRepo.save(userTaskRelation);
    }

    public List<String> getCompletedTasksIdOfSchemaForUser(String schemaId, String userId) {
        return utrRepo.getCompletedTasksIdBySchemaIdAndUserId(userId, schemaId);
    }

    public List<UserTaskRelation> getAllRelationsBySchemaId(String schemaId){
        return utrRepo.getAllBySchemaId(schemaId);
    }
    
    public List<UserTaskRelation> getSchemaStateByUserId(String userId,String schemaId){
        return utrRepo.getAllBySchemaIdAndUserId(schemaId,userId);
    }

    public boolean existsBySchemaIdAndUserIdAndTaskId(String schemaId, String userId, String taskId){
        return utrRepo.existsBySchemaIdAndUserIdAndTaskId(schemaId, userId, taskId);
    }

    public List<String> getAllOpenedSchemasIdToUser(String userId){
        List<String> schemasId = utrRepo.getOpenedSchemasIdOfUser(userId);
        if (schemasId.size()==0)throw new ContentNotFoundException("Курсы не назначены");
        return schemasId;
    }

    public List<String> getOpenedTasksIdBySchemaOfUser(String userId, String schemaId){
        List<String> tasksId = utrRepo.getOpenedTasksIdBySchemaIdAndUserId(userId, schemaId);
        if (tasksId.size()==0)throw new ContentNotFoundException("Данный курс не назначен");
        return tasksId;
    }

    private boolean defaultCheck(String taskId, List<DependencyWithRelationType> dependencies, Set<String> finishedTasksId) {
        return dependencies
                .stream()
                .filter(dep -> !finishedTasksId.contains(dep.getId1()))
                .filter(dep -> !finishedTasksId.contains(dep.getId0()))
                .filter(dep -> dep.getRelationType() == RelationType.WEAK)
                .noneMatch(dep -> dep.getId1().equals(taskId));
    }

    private boolean deepCheck(String taskId, Map<String, AbstractTask> tasksMap,
                              List<DependencyWithRelationType> dependencies,
                              Set<String> finishedTasksId) {
        //bug in future if task in root theme
        return dependencies.stream()
                .filter(dep -> dep.getId1().equals(taskId))
                .map(Dependency::getId0)
                .flatMap(id -> dependencies.stream()
                        .filter(dep -> dep.getId1().equals(id)))
                .map(Dependency::getId0)
                .flatMap(id -> dependencies.stream()
                        .filter(dep -> dep.getId0().equals(id))
                        .map(Dependency::getId1))
                .filter(id -> !tasksMap.get(id).isTheme())
                .allMatch(finishedTasksId::contains);
    }
}