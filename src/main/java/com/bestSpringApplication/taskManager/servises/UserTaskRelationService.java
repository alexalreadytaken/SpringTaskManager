package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelationImpl;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.repos.UserTaskRelationRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
// TODO: 3/28/2021 rename?
public class UserTaskRelationService {

    @NonNull private final UserTaskRelationRepo utrRepo;
    @NonNull private final MasterSchemasService masterSchemasService;

    public void prepareFirstTasks(AbstractStudySchema schema, String studentId){
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();

        List<AbstractTask> availableTasks = tasksMap.values().stream()
                .filter(task -> !task.isTheme())
                .filter(task -> canStartTask(schema,studentId,task))
                .collect(Collectors.toList());

        availableTasks.forEach(task->prepareTask(schema,task,studentId));
    }

    public void prepareTask(AbstractStudySchema schema,AbstractTask task,String studentId){
        UserTaskRelationImpl userTaskRelation = UserTaskRelationImpl.builder()
                .schemaId(schema.getKey())
                .finishConfirmed(false)
                .status(Status.IN_WORK)
                .taskId(task.getId())
                .isFinished(false)
                .userId(studentId)
                .grade(Grade.ONE)
                .build();
        utrRepo.save(userTaskRelation);
    }

    public boolean canStartTask(String schemaKey, String studentId, String taskId){
        AbstractStudySchema schema = masterSchemasService.schemaByKey(schemaKey);
        AbstractTask task = masterSchemasService.taskByIdInSchema(taskId, schemaKey);
        return canStartTask(schema,studentId,task);
    }
    public boolean canStartTask(AbstractStudySchema schema, String studentId, AbstractTask task){
        List<DependencyWithRelationType> dependencies = schema.getDependencies();
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();

        List<DependencyWithRelationType> allTaskParents = dependencies.stream()
                .filter(dependency -> dependency.getId1().equals(task.getId()))
                .collect(Collectors.toList());


        return allTaskParents.stream().allMatch(dependency -> {
            String id0 = splitIdOrDefault(dependency.getId0());

            boolean parentIsRoot = dependencies.stream()
                    .noneMatch(dependency1 -> dependency1.getId1().equals(id0));
            boolean parentIsTheme = tasksMap.get(id0).isTheme();
            return parentIsTheme && parentIsRoot;
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

    public boolean existsBySchemaIdAndUserIdAndTaskId(String schemaId, String userId, String taskId){
        return utrRepo.existsBySchemaIdAndUserIdAndTaskId(schemaId, userId, taskId);
    }

    public boolean saveRelation(UserTaskRelationImpl relation){
        utrRepo.save(relation);
        return true;
    }

    public boolean removeRelation(UserTaskRelationImpl relation){
        utrRepo.delete(relation);
        return true;
    }

    public List<UserTaskRelationImpl> getRelationList(){
        return utrRepo.findAll();
    }

    public Optional<UserTaskRelationImpl> getRelationById(int id){
        return utrRepo.findById(id);
    }

    public Optional<UserTaskRelationImpl> getRelationById(String id){
        try {
            int id0 = Integer.parseInt(id);
            return getRelationById(id0);
        }catch (NumberFormatException ex){
            return Optional.empty();
        }
    }
}





