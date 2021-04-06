package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelationImpl;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.enums.RelationType;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.models.interfaces.Dependency;
import com.bestSpringApplication.taskManager.repos.UserTaskRelationRepo;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.TaskInWorkException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.TaskIsThemeException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
// TODO: 3/28/2021 rename?
public class UserTaskRelationService {

    @NonNull private final UserTaskRelationRepo utrRepo;
    @NonNull private final MasterSchemasService masterSchemasService;

    public boolean canStartTask(String schemaKey, String studentId, String taskId){
        AbstractStudySchema schema = masterSchemasService.schemaByKey(schemaKey);
        AbstractTask task = masterSchemasService.taskByIdInSchema(taskId, schemaKey);
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();
        List<DependencyWithRelationType> dependencies = schema.getDependencies();

        // TODO: 4/4/21 reopen
        if (existsBySchemaIdAndUserIdAndTaskId(schemaKey,studentId,taskId)){
            throw new TaskInWorkException("Задание уже начато");
        }else if (task.isTheme()){
            throw new TaskIsThemeException("Тему начать невозможно!");
        }

        Set<String> finishedTasksId =
                getAllFinishedTasksOfSchemaForTheStudent(schemaKey, studentId)
                        .stream()
                        .map(AbstractTask::getId)
                        .collect(Collectors.toSet());

        boolean parentsOnlyHierarchical = dependencies.stream()
                .filter(dep -> dep.getId1().equals(taskId))
                .allMatch(dep -> dep.getRelationType() == RelationType.HIERARCHICAL);

        boolean taskNotSuccessor = true;
        boolean __NEED_NAME__ = true;

        if (parentsOnlyHierarchical){
            __NEED_NAME__ = dependencies.stream()
                    .filter(dep -> dep.getId1().equals(taskId))
                    .map(Dependency::getId0)
                    .flatMap(id -> dependencies.stream()
                            .filter(dep -> dep.getId1().equals(id)))
                    .map(Dependency::getId0)
                    .flatMap(id -> dependencies.stream()
                            .filter(dep -> dep.getId0().equals(id))
                            .map(Dependency::getId1))
                    //bug in future if task in root theme
                    .filter(id->!tasksMap.get(id).isTheme())
                    .allMatch(finishedTasksId::contains);
        }else {
            taskNotSuccessor = dependencies
                    .stream()
                    .filter(dep->!finishedTasksId.contains(dep.getId1())
                            &&!finishedTasksId.contains(dep.getId0()))
                    .filter(dep -> dep.getRelationType()==RelationType.WEAK)
                    .noneMatch(dep->dep.getId1().equals(taskId));
        }
        return taskNotSuccessor&&__NEED_NAME__;
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

    public List<AbstractTask> getAllFinishedTasksOfSchemaForTheStudent(String schemaKey, String studentId){
        AbstractStudySchema schema = masterSchemasService.schemaByKey(schemaKey);

        List<UserTaskRelationImpl> allOpenedTasksOfSchemaForTheStudent =
                getAllOpenedTasksOfSchemaForTheStudent(schemaKey, studentId);

        return schema.getTasksMap()
                .values().stream()
                .filter(task ->
                        allOpenedTasksOfSchemaForTheStudent.stream()
                                .filter(utr->utr.getTaskId().equals(task.getId()))
                                .findAny()
                                .map(utr->utr.getStatus()==Status.FINISHED
                                        &&utr.getGrade().getIntValue()>=3)
                                .orElse(false))
                .collect(Collectors.toList());
    }

    public List<UserTaskRelationImpl> getAllOpenedTasksOfSchemaForTheStudent(String schemaKey, String studentId) {
        return utrRepo.getAllBySchemaIdAndUserId(schemaKey, studentId);
    }

    public boolean existsBySchemaIdAndUserIdAndTaskId(String schemaId, String userId, String taskId){
        return utrRepo.existsBySchemaIdAndUserIdAndTaskId(schemaId, userId, taskId);
    }

    public void prepareFirstTasks(AbstractStudySchema schema, String studentId){
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();

        List<AbstractTask> availableTasks = tasksMap.values().stream()
                .filter(task -> !task.isTheme())
                .filter(task -> firstCheckTask(schema,task))
                .collect(Collectors.toList());

        availableTasks.forEach(task->prepareTask(schema,task,studentId));
    }

    public void prepareTask(AbstractStudySchema schema,AbstractTask task,String studentId){
        UserTaskRelationImpl userTaskRelation = UserTaskRelationImpl.builder()
                .schemaId(schema.getKey())
                .status(Status.IN_WORK)
                .taskId(task.getId())
                .userId(studentId)
                .grade(Grade.ONE)
                .build();
        utrRepo.save(userTaskRelation);
    }

    // TODO: 4/6/21 <-----------------------------CHECK EXISTS-------------------------------->

    public List<String> getAllOpenedSchemasKeysToStudent(String studentId){
        return utrRepo.getAllOpenedSchemasKeysToStudent(studentId);
    }

    public List<String> getOpenedTasksIdOfStudent(String userId){
        return utrRepo.getAllOpenedSchemasKeysToStudent(userId);
    }

    public List<String> getOpenedTasksIdBySchemaOfStudent(String userId,String schemaId){
        return utrRepo.getOpenedTasksIdBySchemaOfStudent(userId, schemaId);
    }
}