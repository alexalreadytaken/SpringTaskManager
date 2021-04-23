package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.enums.RelationType;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.models.interfaces.Dependency;
import com.bestSpringApplication.taskManager.servises.interfaces.SchemasProvider;
import com.bestSpringApplication.taskManager.servises.interfaces.StudyService;
import com.bestSpringApplication.taskManager.servises.interfaces.StudyStateService;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.TaskClosedException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.TaskFinishedException;
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

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersStudyService implements StudyService {

    @NonNull private final SchemasProvider schemasProvider;
    @NonNull private final StudyStateService studyStateService;

    @NonNull private final UserService userService;

    public void setSchemaToUser(String userId,String schemaId){
        log.trace("trying prepare schema '{}' to user '{}'",schemaId,userId);
        userService.validateExistsAndContainsRole(userId,Role.STUDENT);
        studyStateService.prepareSchema(schemasProvider.getSchemaById(schemaId),userId);
    }

    // TODO: 4/4/21 reopen
    public boolean canStartTask(String schemaId, String userId, String taskId){
        log.trace("check possibility starting task '{}' in schema '{}' for user '{}'",
                taskId,schemaId,userId);
        AbstractStudySchema schema = schemasProvider.getSchemaById(schemaId);
        AbstractTask task = schemasProvider.getTaskByIdInSchema(taskId, schemaId);
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();
        List<DependencyWithRelationType> dependencies = schema.getDependenciesWithRelationType();
        validateTaskCondition(schemaId, userId, taskId, task);
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

    public List<AbstractStudySchema> getUserSchemas(String userId) {
        userService.validateExistsAndContainsRole(userId,Role.STUDENT);
        List<AbstractStudySchema> schemas = studyStateService
                .getOpenedSchemasIdOfUser(userId)
                .stream()
                .map(schemasProvider::getSchemaById)
                .collect(toList());
        log.trace("request for all schemas of user '{}',return = {} ",userId,schemas);
        return schemas;
    }

    public List<AbstractTask> getUserSchemasRootTasks(String userId){
        return getUserSchemas(userId).stream()
                .map(AbstractStudySchema::getRootTask)
                .collect(toList());
    }

    public List<AbstractTask> getOpenedUserTasksOfSchema(String userId, String schemaId){
        List<String> tasksId = studyStateService.getOpenedTasksIdBySchemaOfUser(userId, schemaId);
        Map<String, AbstractTask> tasksMap = schemasProvider.getSchemaById(schemaId).getTasksMap();
        return tasksId.stream()
                .map(tasksMap::get)
                .collect(toList());
    }

    private void startTask(String schemaId, String userId, String taskId) {
        log.trace("trying start task '{}' in schema '{}' for user '{}'",taskId,schemaId,userId);
        schemasProvider.getTaskByIdInSchema(taskId, schemaId);
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

    private void validateTaskCondition(String schemaId, String userId, String taskId, AbstractTask task) {
        if (task.isTheme()){
            throw new TaskIsThemeException("Тему начать невозможно!");
        }else if (studyStateService.taskInWork(schemaId, userId, taskId)){
            throw new TaskInWorkException("Задание уже начато");
        } else if (studyStateService.taskFinished(schemaId, userId, taskId)){
            throw new TaskFinishedException("Задание уже завершено");
        }
    }
}