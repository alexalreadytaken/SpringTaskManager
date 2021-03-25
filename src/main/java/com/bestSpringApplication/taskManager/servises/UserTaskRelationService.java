package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.classes.DependencyImpl;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.TaskImpl;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelationImpl;
import com.bestSpringApplication.taskManager.models.interfaces.Dependency;
import com.bestSpringApplication.taskManager.repos.UserTaskRelationRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTaskRelationService {

    @NonNull private final UserTaskRelationRepo utrRepo;

    public void prepareFirstTasks(AbstractStudySchema schema, String studentId){
        List<DependencyWithRelationType> dependencies = schema.getDependencies();
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();
        List<AbstractTask> availableTasks = new ArrayList<>();
        dependencies.forEach(dependency -> {
            String id0 = dependency.getId0();
            boolean taskIsNotSuccessor = dependencies.stream()
                    .map(DependencyImpl::getId1)
                    .noneMatch(id1 -> id1.equals(id0));
            if (taskIsNotSuccessor) availableTasks.add(tasksMap.get(id0));
        });
        availableTasks.forEach(task-> prepareTask(schema,task,studentId));
    }

    public void prepareTask(AbstractStudySchema schema,AbstractTask task,String studentId){
        task.setOpened(true);
        UserTaskRelationImpl userTaskRelation = UserTaskRelationImpl.builder()
                .schemaId(schema.getKey())
                .finishConfirmed(false)
                .grade(Grade.IN_WORK)
                .taskId(task.getId())
                .isFinished(false)
                .userId(studentId)
                .build();
        utrRepo.save(userTaskRelation);
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





