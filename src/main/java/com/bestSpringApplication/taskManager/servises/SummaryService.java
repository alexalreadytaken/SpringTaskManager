package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.classes.Summary;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.servises.interfaces.SchemasProvider;
import com.bestSpringApplication.taskManager.servises.interfaces.StudyStateService;
import com.bestSpringApplication.taskManager.servises.interfaces.SummaryProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryService implements SummaryProvider {

    @NonNull private final StudyStateService studyStateService;

    public List<Summary> getTasksSummaryBySchema(String schemaId){
        log.trace("trying get summary of schema '{}'",schemaId);
        List<UserTaskRelation> relations = studyStateService.getAllRelationsBySchemaId(schemaId);
        return relations.stream()
                .map(UserTaskRelation::getTaskId)
                .distinct()
                .map(id->{
                    List<UserTaskRelation> taskRelations = relations.stream()
                            .filter(utr -> utr.getTaskId().equals(id))
                            .collect(toList());
                    return getSummaryUniversal(id, taskRelations);
                }).collect(toList());
    }

    public Summary getSummaryBySchemaIdAndTaskId(String schemaId, String taskId){
        log.trace("trying get summary of schema '{}' and task '{}'",schemaId,taskId);
        List<UserTaskRelation> relations = studyStateService.getRelationsBySchemaIdAndTaskId(schemaId, taskId);
        return getSummaryUniversal(taskId, relations);
    }

    public Summary getUserSchemaSummary(String schemaId,String userId){
        log.trace("trying get schema '{}' summary of user '{}'",schemaId,userId);
        List<UserTaskRelation> schemaStateByUserId = studyStateService.getSchemaStateByUserId(userId, schemaId);
        return getSummaryUniversal(schemaId, schemaStateByUserId);
    }

    public List<UserTaskRelation> getUserTasksState(String schemaId, String userId){
        log.trace("trying get schema '{}' state of user '{}'",schemaId,userId);
        return studyStateService.getSchemaStateByUserId(userId,schemaId);
    }

    private Summary getSummaryUniversal(String entityId, List<UserTaskRelation> relations) {
        return new Summary(entityId,
                getPercentCompleteTasks(relations),
                getAverageTasksGrade(relations),
                getCountByTasksGrade(relations));
    }

    private double getPercentCompleteTasks(List<UserTaskRelation> taskRelations) {
        long finishedCount = taskRelations.stream()
                .map(UserTaskRelation::getStatus)
                .filter(Status.FINISHED::isInstance)
                .count();
        return (double)finishedCount/(double)taskRelations.size();
    }

    private double getAverageTasksGrade(List<UserTaskRelation> taskRelations) {
        return taskRelations.stream()
                .map(UserTaskRelation::getGrade)
                .map(Grade::getIntValue)
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);
    }

    private Map<Grade, Long> getCountByTasksGrade(List<UserTaskRelation> taskRelations) {
        return taskRelations.stream()
                .map(UserTaskRelation::getGrade)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()));
    }
}
