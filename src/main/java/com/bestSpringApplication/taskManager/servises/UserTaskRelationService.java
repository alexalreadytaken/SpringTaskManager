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
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.ContentNotFoundException;
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
public class UserTaskRelationService {

    @NonNull private final UserTaskRelationRepo utrRepo;
    @NonNull private final MasterSchemasService masterSchemasService;

    public boolean canStartTask(String schemaId, String userId, String taskId){
        AbstractStudySchema schema = masterSchemasService.schemaById(schemaId);
        AbstractTask task = masterSchemasService.taskByIdInSchema(taskId, schemaId);
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();
        List<DependencyWithRelationType> dependencies = schema.getDependencies();

        // TODO: 4/4/21 reopen
        if (existsBySchemaIdAndUserIdAndTaskId(schemaId,userId,taskId)){
            throw new TaskInWorkException("Задание уже начато");
        }else if (task.isTheme()){
            throw new TaskIsThemeException("Тему начать невозможно!");
        }

        Set<String> finishedTasksId =
                getAllCompletedTasksOfSchemaForTheStudent(schemaId, userId)
                        .stream()
                        .map(AbstractTask::getId)
                        .collect(Collectors.toSet());

        boolean parentsOnlyHierarchical = dependencies.stream()
                .filter(dep -> dep.getId1().equals(taskId))
                .allMatch(dep -> dep.getRelationType() == RelationType.HIERARCHICAL);

        boolean taskNotSuccessor = true;
        boolean TaskNotSuccessorDeep = true;

        if (parentsOnlyHierarchical){
            TaskNotSuccessorDeep = dependencies.stream()
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
        return taskNotSuccessor&&TaskNotSuccessorDeep;
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

    public void prepareFirstTasks(AbstractStudySchema schema, String userId){
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();

        tasksMap.values().stream()
                .filter(task -> !task.isTheme())
                .filter(task -> firstCheckTask(schema,task))
                .forEach(task->prepareTask(schema,task,userId));
    }

    public void prepareTask(AbstractStudySchema schema,AbstractTask task,String userId){
        UserTaskRelationImpl userTaskRelation = UserTaskRelationImpl.builder()
                .schemaId(schema.getId())
                .status(Status.IN_WORK)
                .taskId(task.getId())
                .userId(userId)
                .grade(Grade.ONE)
                .build();
        utrRepo.save(userTaskRelation);
    }

    public List<AbstractTask> getAllCompletedTasksOfSchemaForTheStudent(String schemaId, String userId){
        AbstractStudySchema schema = masterSchemasService.schemaById(schemaId);

        List<UserTaskRelationImpl> allOpenedTasksOfSchemaForTheStudent =
                getAllOpenedTasksOfSchemaForTheStudent(schemaId, userId);

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

    public List<UserTaskRelationImpl> getAllOpenedTasksOfSchemaForTheStudent(String schemaId, String userId) {
        return utrRepo.getAllBySchemaIdAndUserId(schemaId, userId);
    }

    public boolean existsBySchemaIdAndUserIdAndTaskId(String schemaId, String userId, String taskId){
        return utrRepo.existsBySchemaIdAndUserIdAndTaskId(schemaId, userId, taskId);
    }

    public List<String> getAllOpenedSchemasIdToStudent(String userId){
        List<String> schemasId = utrRepo.getAllOpenedSchemasIdToStudent(userId);
        if (schemasId.size()==0)throw new ContentNotFoundException("Курсы не назначены");
        return schemasId;
    }

    public List<String> getOpenedTasksIdOfStudent(String userId){
        return utrRepo.getAllOpenedSchemasIdToStudent(userId);
    }

    public List<String> getOpenedTasksIdBySchemaOfStudent(String userId,String schemaId){
        List<String> tasksId = utrRepo.getOpenedTasksIdBySchemaOfStudent(userId, schemaId);
        if (tasksId.size()==0)throw new ContentNotFoundException("Данный курс не назначен");
        return tasksId;
    }
}