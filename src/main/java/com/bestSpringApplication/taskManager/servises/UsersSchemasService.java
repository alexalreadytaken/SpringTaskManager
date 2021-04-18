package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.classes.GroupTaskSummary;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import com.bestSpringApplication.taskManager.models.enums.RelationType;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.models.interfaces.Dependency;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.TaskClosedException;
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

    public boolean canStartTask(String schemaId, String userId, String taskId){
        AbstractStudySchema schema = masterSchemasService.getSchemaById(schemaId);
        AbstractTask task = masterSchemasService.getTaskByIdInSchema(taskId, schemaId);
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();
        List<DependencyWithRelationType> dependencies = schema.getDependenciesWithRelationType();

        // TODO: 4/4/21 reopen
        if (utrService.taskInWork(schemaId,userId,taskId)){
            throw new TaskInWorkException("Задание уже начато");
        }else if (task.isTheme()){
            throw new TaskIsThemeException("Тему начать невозможно!");
        }

        List<String> ids = utrService.getCompletedTasksIdOfSchemaForUser(schemaId, userId);
        Set<String> finishedTasksId = new HashSet<>(ids);

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

    public void forceStartTask(String schemaId, String userId, String taskId){
        startTask(schemaId, userId, taskId);
    }

    public void startTaskWithValidation(String schemaId, String userId, String taskId){
        if (canStartTask(schemaId, userId, taskId)){
            startTask(schemaId, userId, taskId);
        }else {
            throw new TaskClosedException("Задание невозможно начать (REWRITE EX TEXT)");
        }
    }

    public Map<String, AbstractStudySchema> getUserSchemas(String userId) {
        userService.validateExistsAndContainsRole(userId,Role.STUDENT);
        List<String> allOpenedSchemasToUser = utrService.getOpenedSchemasIdOfUser(userId);
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

    // TODO: 4/15/21 similar for user
    public List<GroupTaskSummary> getAllUsersTasksSummary(String schemaId){
        List<UserTaskRelation> relations = utrService.getAllRelationsBySchemaId(schemaId);
        return relations.stream()
                .map(UserTaskRelation::getTaskId)
                .distinct()
                .map(id->{
                    List<UserTaskRelation> taskRelations = relations.stream()
                            .filter(utr -> utr.getTaskId().equals(id))
                            .collect(toList());
                    return new GroupTaskSummary(id,
                            utrService.getPercentCompleteTasks(taskRelations),
                            utrService.getAverageTasksGrade(taskRelations).orElse(0.0),
                            utrService.getCountByTasksGrade(taskRelations));
                }).collect(toList());
    }

    public List<UserTaskRelation> getUserTasksSummary(String schemaId, String userId){
        return utrService.getSchemaStateByUserId(userId,schemaId);
    }

    private void startTask(String schemaId, String userId, String taskId) {
        log.trace("trying start task '{}' in schema '{}' for student '{}'",taskId,schemaId,userId);
        masterSchemasService.getTaskByIdInSchema(taskId, schemaId);
        utrService.openTask(schemaId, userId, taskId);
    }

    private boolean defaultCheck(String taskId, List<DependencyWithRelationType> dependencies, Set<String> finishedTasksId) {
        return dependencies.stream()
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




