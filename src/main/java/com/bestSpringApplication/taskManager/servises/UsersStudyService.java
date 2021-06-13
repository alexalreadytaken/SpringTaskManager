package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.classes.StudySchema;
import com.bestSpringApplication.taskManager.models.classes.StudyTask;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.entities.User;
import com.bestSpringApplication.taskManager.models.enums.RelationType;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.models.interfaces.Dependency;
import com.bestSpringApplication.taskManager.servises.interfaces.GroupService;
import com.bestSpringApplication.taskManager.servises.interfaces.SchemasService;
import com.bestSpringApplication.taskManager.servises.interfaces.StudyService;
import com.bestSpringApplication.taskManager.servises.interfaces.StudyStateService;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.*;
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

    @NonNull private final SchemasService schemasService;
    @NonNull private final StudyStateService studyStateService;
    @NonNull private final GroupService groupService;

    public void setSchemaToUser(String userId,String schemaId){
        log.trace("trying prepare schema '{}' to user '{}'",schemaId,userId);
        StudySchema schema = schemasService.getSchemaById(schemaId);
        studyStateService.prepareSchema(schema,userId);
    }

    public void setSchemaToGroup(String groupId, String schemaId) {
        log.trace("trying prepare schema '{}' to group '{}'",schemaId,groupId);
        groupService.getGroupById(groupId)
                .getUsers().stream()
                .map(User::getStringId)
                .forEach(userId->setSchemaToUser(userId,schemaId));
    }

    public boolean canStartTask(String schemaId, String userId, String taskId){
        log.trace("check possibility starting task '{}' in schema '{}' for user '{}'",taskId,schemaId,userId);
        StudySchema schema = schemasService.getSchemaById(schemaId);
        StudyTask task = schemasService.getTaskByIdInSchema(taskId, schemaId);
        validateTaskCondition(schemaId, userId, taskId, task);
        Map<String, StudyTask> tasksMap = schema.getTasksMap();
        List<DependencyWithRelationType> dependencies = schema.getDependenciesWithRelationType();
        List<String> ids = studyStateService.getCompletedTasksIdOfSchemaForUser(schemaId, userId);
        Set<String> finishedTasksId = new HashSet<>(ids);
        boolean parentsOnlyHierarchical = dependencies.stream()
                .filter(dep->dep.getId1().equals(taskId))
                .map(DependencyWithRelationType::getRelationType)
                .allMatch(RelationType.HIERARCHICAL::isInstance);
        return parentsOnlyHierarchical
                ? deepCheck(taskId, tasksMap, dependencies, finishedTasksId)
                : defaultCheck(taskId, dependencies, finishedTasksId);
    }

    public void reopenTask(String schemaId, String userId, String taskId){
        log.trace("reopen task '{}' of schema '{}' for user '{}'",taskId,schemaId,userId);
        studyStateService.setStatusForUserTask(schemaId,userId,taskId,Status.REOPENED);
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

    public List<StudyTask> getAvailableToStartUserTasks(String userId) {
        return studyStateService.getAllUserStatesByStatus(userId,Status.CLOSED)
                .stream()
                .filter(state->canStartTask(state.getSchemaId(),state.getUserId(),state.getTaskId()))
                .map(state->schemasService.getTaskByIdInSchema(state.getTaskId(),state.getSchemaId()))
                .collect(toList());
    }

    public List<StudyTask> getAvailableToStartUserTasks(String userId, String schemaId) {
        return studyStateService.getAllUserStatesBySchemaAndStatus(userId,schemaId,Status.CLOSED)
                .stream()
                .filter(state->canStartTask(state.getSchemaId(),state.getUserId(),state.getTaskId()))
                .map(state->schemasService.getTaskByIdInSchema(state.getTaskId(),state.getSchemaId()))
                .collect(toList());
    }

    public List<StudyTask> getUserSchemasRootTasks(String userId){
        return getUserSchemas(userId).stream()
                .map(StudySchema::getRootTask)
                .collect(toList());
    }

    public List<StudyTask> getOpenedUserTasks(String userId) {
        return studyStateService.getAllUserStatesByStatus(userId,Status.IN_WORK).stream()
                .map(state->schemasService.getTaskByIdInSchema(state.getTaskId(),state.getSchemaId()))
                .collect(toList());
    }

    public List<StudyTask> getOpenedUserTasks(String userId, String schemaId){
        List<String> tasksId = studyStateService.getOpenedTasksIdBySchemaOfUser(userId, schemaId);
        Map<String, StudyTask> tasksMap = schemasService.getSchemaById(schemaId).getTasksMap();
        return tasksId.stream()
                .map(tasksMap::get)
                .collect(toList());
    }

    private void startTask(String schemaId, String userId, String taskId) {
        log.trace("trying start task '{}' in schema '{}' for user '{}'",taskId,schemaId,userId);
        studyStateService.openTask(schemaId, userId, taskId);
    }

    private List<StudySchema> getUserSchemas(String userId) {
        return studyStateService
                .getOpenedSchemasIdOfUser(userId)
                .stream()
                .filter(schemasService::schemaExists)
                .map(schemasService::getSchemaById)
                .collect(toList());
    }

    private boolean defaultCheck(String taskId, List<DependencyWithRelationType> dependencies, Set<String> finishedTasksId) {
        return dependencies.stream()
                .filter(dep -> !finishedTasksId.contains(dep.getId1()))
                .filter(dep -> !finishedTasksId.contains(dep.getId0()))
                .filter(dep -> dep.getRelationType() == RelationType.WEAK)
                .noneMatch(dep -> dep.getId1().equals(taskId));
    }

    private boolean deepCheck(String taskId, Map<String, StudyTask> tasksMap,
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

    private void validateTaskCondition(String schemaId, String userId, String taskId, StudyTask task) {
        if (task.isTheme()){
            throw new TaskIsThemeException("Тему начать невозможно!");
        }else if (studyStateService.taskInWork(schemaId, userId, taskId)){
            throw new TaskInWorkException("Задание уже начато");
        } else if (studyStateService.taskFinished(schemaId, userId, taskId)){
            throw new TaskFinishedException("Задание уже завершено");
        }
    }
}