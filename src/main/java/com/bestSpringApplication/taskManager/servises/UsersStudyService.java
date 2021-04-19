package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.classes.GroupTaskSummary;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import com.bestSpringApplication.taskManager.models.enums.RelationType;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.models.interfaces.Dependency;
import com.bestSpringApplication.taskManager.servises.interfaces.*;
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
public class UsersStudyService implements SummaryProviderService, StudyService {

    @NonNull private final UserService userService;

    @NonNull private final SchemasProviderService schemasProviderService;
    @NonNull private final StudyStateService studyStateService;
    @NonNull private final SummaryHandler summaryHandler;

    public void setSchemaToUser(String userId,String schemaId){
        log.trace("trying prepare schema '{}' to user '{}'",schemaId,userId);
        userService.validateExistsAndContainsRole(userId,Role.STUDENT);
        studyStateService.prepareSchema(schemasProviderService.getSchemaById(schemaId),userId);
    }

    public boolean canStartTask(String schemaId, String userId, String taskId){
        AbstractStudySchema schema = schemasProviderService.getSchemaById(schemaId);
        AbstractTask task = schemasProviderService.getTaskByIdInSchema(taskId, schemaId);
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();
        List<DependencyWithRelationType> dependencies = schema.getDependenciesWithRelationType();

        // TODO: 4/4/21 reopen
        if (studyStateService.taskInWork(schemaId,userId,taskId)){
            throw new TaskInWorkException("Задание уже начато");
        }else if (task.isTheme()){
            throw new TaskIsThemeException("Тему начать невозможно!");
        }

        List<String> ids = studyStateService.getCompletedTasksIdOfSchemaForUser(schemaId, userId);
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
        List<String> allOpenedSchemasToUser = studyStateService.getOpenedSchemasIdOfUser(userId);
        Map<String, AbstractStudySchema> schemasMap = allOpenedSchemasToUser.stream()
                .map(schemasProviderService::getSchemaById)
                .collect(toMap(AbstractStudySchema::getId, Function.identity()));
        log.trace("request for all schemas of user '{}',return = {} ",userId,schemasMap.keySet());
        return schemasMap;
    }

    public List<AbstractTask> getUserSchemasRootTasks(String userId){
        return getUserSchemas(userId)
                .values().stream()
                .map(AbstractStudySchema::getRootTask)
                .collect(toList());
    }

    public List<AbstractTask> getOpenedUserTasksOfSchema(String userId, String schemaId){
        List<String> tasksId = studyStateService.getOpenedTasksIdBySchemaOfUser(userId, schemaId);
        Map<String, AbstractTask> tasksMap = schemasProviderService.getSchemaById(schemaId).getTasksMap();
        return tasksId.stream()
                .map(tasksMap::get)
                .collect(toList());
    }

    // TODO: 4/15/21 similar for user
    public List<GroupTaskSummary> getTasksSummaryBySchema(String schemaId){
        List<UserTaskRelation> relations = studyStateService.getAllRelationsBySchemaId(schemaId);
        return relations.stream()
                .map(UserTaskRelation::getTaskId)
                .distinct()
                .map(id->{
                    List<UserTaskRelation> taskRelations = relations.stream()
                            .filter(utr -> utr.getTaskId().equals(id))
                            .collect(toList());
                    return new GroupTaskSummary(id,
                            summaryHandler.getPercentCompleteTasks(taskRelations),
                            summaryHandler.getAverageTasksGrade(taskRelations).orElse(0.0),
                            summaryHandler.getCountByTasksGrade(taskRelations));
                }).collect(toList());
    }

    public GroupTaskSummary getSummaryBySchemaIdAndTaskId(String schemaId,String taskId){
        List<UserTaskRelation> relations = studyStateService.getRelationsBySchemaIdAndTaskId(schemaId, taskId);
        return new GroupTaskSummary(taskId,
                summaryHandler.getPercentCompleteTasks(relations),
                summaryHandler.getAverageTasksGrade(relations).orElse(0.0),
                summaryHandler.getCountByTasksGrade(relations));
    }

    public List<UserTaskRelation> getUserTasksSummary(String schemaId, String userId){
        return studyStateService.getSchemaStateByUserId(userId,schemaId);
    }

    private void startTask(String schemaId, String userId, String taskId) {
        log.trace("trying start task '{}' in schema '{}' for user '{}'",taskId,schemaId,userId);
        schemasProviderService.getTaskByIdInSchema(taskId, schemaId);
        studyStateService.openTask(schemaId, userId, taskId);
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
        //may bug in future if task in root theme
        return dependencies.stream()
                .filter(dep -> dep.getId1().equals(taskId))
                .map(Dependency::getId0)
                .flatMap(taskParentId -> dependencies.stream()
                        .filter(dep -> dep.getId1().equals(taskParentId)))
                .map(Dependency::getId0)
                .flatMap(parentIdOfTaskParent -> dependencies.stream()
                        .filter(dep -> dep.getId0().equals(parentIdOfTaskParent))
                        .map(Dependency::getId1))
                .filter(id -> !tasksMap.get(id).isTheme())
                .allMatch(finishedTasksId::contains);
    }
}




