package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.study.classes.TaskImpl;
import com.bestSpringApplication.taskManager.models.study.classes.UserTaskRelationImpl;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
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
public class UserTaskRelationService {

    @NonNull private final UserTaskRelationRepo utrRepo;


    /*FIXME: 2/18/2021 optimize optimize optimize optimize optimize optimize optimize optimize
            or no? performance 0-16 ms */
    public void prepareFirstTasks(AbstractStudySchema schema, String studentId){
        List<Dependency> dependencies = schema.getDependencies();
        Map<String, AbstractTask> tasksMap = schema.getTasksMap();

        List<AbstractTask> openedTasks = tasksMap
                .values().stream()
                .filter(task -> {
                    boolean parentIsTheme = true;

                    if (task instanceof TaskImpl){
                        TaskImpl task0 = (TaskImpl) task;
                        parentIsTheme = Optional
                                .ofNullable(tasksMap.get(task0.getParentId()))
                                .map(AbstractTask::isTheme)
                                .orElse(true);
                    }

                    boolean parentsInDependenciesIsThemes = dependencies.stream()
                            .filter(depend -> depend.getId1().equals(task.getId()))
                            .map(depend -> tasksMap.get(depend.getId0()))
                            .allMatch(AbstractTask::isTheme);

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

    }

    public boolean existsBySchemeIdAndUserIdAndTaskId(String schemeId,String userId,String taskId){
        return utrRepo.existsBySchemeIdAndUserIdAndTaskId(schemeId, userId, taskId);
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





