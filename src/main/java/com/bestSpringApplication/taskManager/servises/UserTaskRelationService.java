package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.study.implementations.UserTaskRelationImpl;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import com.bestSpringApplication.taskManager.models.study.interfaces.UserTaskRelation;
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


    /*FIXME: 2/18/2021 optimize optimize optimize optimize optimize optimize optimize optimize
            or no? performance 0-16 ms */
    public boolean prepareFirstTasks(StudySchema schema, String studentId){
        List<Dependency> dependencies = schema.getDependencies();
        Map<String, Task> tasksMap = schema.getTasksMap();

        List<Task> openedTasks = tasksMap
                .values().stream()
                .filter(task -> {
                    boolean parentIsTheme = Optional
                            .ofNullable(tasksMap.get(task.getParentId()))
                            .map(Task::isTheme)
                            .orElse(true);

                    boolean parentsInDependenciesIsThemes = dependencies.stream()
                            .filter(depend -> depend.getId1().equals(task.getId()))
                            .map(depend -> tasksMap.get(depend.getId0()))
                            .allMatch(Task::isTheme);

                    return parentIsTheme && parentsInDependenciesIsThemes && !task.isTheme();
                }).collect(Collectors.toList());

        openedTasks.forEach(task->{
            UserTaskRelationImpl userTaskRelation = UserTaskRelationImpl.builder()
                    .schemeId(schema.getRootTask().getName())
                    .finishConfirmed(false)
                    .grade(Grade.IN_WORK)
                    .taskId(task.getId())
                    .isFinished(false)
                    .userId(studentId)
                    .build();
            utrRepo.save(userTaskRelation);
        });
        
        return true;
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





