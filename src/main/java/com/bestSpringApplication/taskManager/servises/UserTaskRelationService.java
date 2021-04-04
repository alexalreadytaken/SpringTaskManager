package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelationImpl;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.enums.RelationType;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.repos.UserTaskRelationRepo;
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
    @NonNull private final StudentSchemasService studentSchemasService;

    // FIXME: 4/3/21 what do with themes
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

    public boolean canStartTask(String schemaKey, String studentId, String taskId){
        AbstractStudySchema schema = masterSchemasService.schemaByKey(schemaKey);
        AbstractTask task = masterSchemasService.taskByIdInSchema(taskId, schemaKey);
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();
        List<DependencyWithRelationType> dependencies = schema.getDependencies();

        // TODO: 4/4/21 reopen
        if (task.isTheme()||existsBySchemaIdAndUserIdAndTaskId(schemaKey,studentId,taskId)){
            return false;
        }

        Set<String> finishedTasksId = studentSchemasService
                .getAllFinishedTasksOfSchemaForTheStudent(schemaKey, studentId)
                .stream()
                .map(AbstractTask::getId)
                .collect(Collectors.toSet());

        List<DependencyWithRelationType> themeParents = dependencies
                .stream()
                .filter(dependency -> dependency.getId1().equals(taskId))
                .filter(dependency -> tasksMap.get(splitIdOrDefault(dependency.getId0())).isTheme())
                .collect(Collectors.toList());

        Boolean superValidate = themeParents
                .stream()
                .map(dependency ->
                        dependencies
                                .stream()
                                .filter(dependency1 -> dependency1.getId1().equals(dependency.getId0()))
                                .filter(dependency1 -> dependency1.getRelationType() == RelationType.WEAK)
                                .map(dependency1 -> splitIdOrDefault(dependency1.getId1()))
                                .filter(id->!tasksMap.get(id).isTheme())
                                .allMatch(finishedTasksId::contains))
                .reduce(true, (a, b) -> a && b);


        boolean taskNotSuccessor = dependencies
                .stream()
                .filter(dependency->!finishedTasksId.contains(dependency.getId1())
                        &&!finishedTasksId.contains(splitIdOrDefault(dependency.getId0())))
                .filter(dependency -> dependency.getRelationType()==RelationType.WEAK)
                .noneMatch(dependency->dependency.getId1().equals(taskId));

        return taskNotSuccessor&&superValidate;
    }

    public boolean firstCheckTask(AbstractStudySchema schema,AbstractTask task){
        List<DependencyWithRelationType> dependencies = schema.getDependencies();
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();

        return dependencies.stream()
                .filter(dependency -> dependency.getId1().equals(task.getId()))
                .allMatch(dependency -> {
                    AbstractTask taskParent = tasksMap.get(splitIdOrDefault(dependency.getId0()));
                    boolean parentHierarchicalAndTheme = taskParent.isTheme() &&
                            dependency.getRelationType() == RelationType.HIERARCHICAL;
                    boolean parentsOfParentValid = firstCheckTask(schema, taskParent);
                    return parentHierarchicalAndTheme&&parentsOfParentValid;
                });
    }

    private String splitIdOrDefault(String id){
        try {
            id = id.split("\\.")[1];
            return id;
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return id;
        }
    }

    public List<UserTaskRelationImpl> getAllOpenedTasksOfSchemaForTheStudent(String schemaKey, String studentId) {
        return utrRepo.getAllBySchemaIdAndUserId(schemaKey, studentId);
    }

    public boolean existsBySchemaIdAndUserIdAndTaskId(String schemaId, String userId, String taskId){
        return utrRepo.existsBySchemaIdAndUserIdAndTaskId(schemaId, userId, taskId);
    }

}





