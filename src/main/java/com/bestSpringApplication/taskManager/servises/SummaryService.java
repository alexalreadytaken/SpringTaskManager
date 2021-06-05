package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.classes.Summary;
import com.bestSpringApplication.taskManager.models.entities.UserTaskState;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.servises.interfaces.StudyStateService;
import com.bestSpringApplication.taskManager.servises.interfaces.SummaryProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryService implements SummaryProvider {

    @NonNull private final StudyStateService studyStateService;

    public List<Summary> getTasksSummaryBySchema(String schemaId){
        log.trace("trying get summary of schema '{}'",schemaId);
        List<UserTaskState> relations = studyStateService.getAllStateBySchemaId(schemaId);
        return relations.stream()
                .map(UserTaskState::getTaskId)
                .distinct()
                .map(id->{
                    List<UserTaskState> taskRelations = relations.stream()
                            .filter(utr -> utr.getTaskId().equals(id))
                            .collect(toList());
                    return getSummaryUniversal(id, taskRelations);
                }).collect(toList());
    }

    public Summary getSummaryBySchemaIdAndTaskId(String schemaId, String taskId){
        log.trace("trying get summary of schema '{}' and task '{}'",schemaId,taskId);
        List<UserTaskState> relations = studyStateService.getTaskStateInSchema(schemaId, taskId);
        return getSummaryUniversal(taskId, relations);
    }

    public Summary getUserSchemaSummary(String schemaId,String userId){
        log.trace("trying get schema '{}' summary of user '{}'",schemaId,userId);
        List<UserTaskState> schemaStateByUserId = studyStateService.getSchemaStateByUserId(userId, schemaId);
        return getSummaryUniversal(schemaId, schemaStateByUserId);
    }

    private Summary getSummaryUniversal(String entityId, List<UserTaskState> states) {
        return new Summary(entityId,
                "TODO",
                getPercentFinishedTasks(states),
                getAverageTasksGrade(states),
                getMinPercentComplete(states),
                getMaxPercentComplete(states));
    }

    private double getMinPercentComplete(List<UserTaskState> taskStates){
        return taskStates.stream()
                .map(UserTaskState::getPercentComplete)
                .mapToDouble(Double::valueOf)
                .min()
                .orElse(0.0);
    }

    private double getMaxPercentComplete(List<UserTaskState> taskStates){
        return taskStates.stream()
                .map(UserTaskState::getPercentComplete)
                .mapToDouble(Double::valueOf)
                .max()
                .orElse(0.0);
    }

    private double getPercentFinishedTasks(List<UserTaskState> taskStates) {
        long finishedCount = taskStates.stream()
                .filter(uts->uts.getPercentComplete()==100)
                .filter(uts->uts.getStatus()==Status.FINISHED)
                .count();
        return (double)finishedCount/(double)taskStates.size();
    }

    private double getAverageTasksGrade(List<UserTaskState> taskStates) {
        return taskStates.stream()
                .map(UserTaskState::getPercentComplete)
                .mapToDouble(Double::valueOf)
                .average()
                .orElse(0.0);
    }


}
