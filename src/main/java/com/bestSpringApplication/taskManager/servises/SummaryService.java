package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.classes.GroupTaskSummary;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.servises.interfaces.StudyStateService;
import com.bestSpringApplication.taskManager.servises.interfaces.SummaryProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryService implements SummaryProvider {

    @NonNull private final StudyStateService studyStateService;

    // TODO: 4/15/21 similar for user
    public List<GroupTaskSummary> getTasksSummaryBySchema(String schemaId){
        List<UserTaskRelation> relations = studyStateService.getAllRelationsBySchemaId(schemaId);
        return relations.stream()
                .map(UserTaskRelation::getTaskId)
                .distinct()
                .map(id->{
                    List<UserTaskRelation> taskRelations = relations.stream()
                            .filter(utr -> utr.getTaskId().equals(id))
                            .collect(toList());
                    return new GroupTaskSummary(id,
                            getPercentCompleteTasks(taskRelations),
                            getAverageTasksGrade(taskRelations).orElse(0.0),
                            getCountByTasksGrade(taskRelations));
                }).collect(toList());
    }

    public GroupTaskSummary getSummaryBySchemaIdAndTaskId(String schemaId,String taskId){
        List<UserTaskRelation> relations = studyStateService.getRelationsBySchemaIdAndTaskId(schemaId, taskId);
        return new GroupTaskSummary(taskId,
                getPercentCompleteTasks(relations),
                getAverageTasksGrade(relations).orElse(0.0),
                getCountByTasksGrade(relations));
    }

    public List<UserTaskRelation> getUserTasksSummary(String schemaId, String userId){
        return studyStateService.getSchemaStateByUserId(userId,schemaId);
    }

    private double getPercentCompleteTasks(List<UserTaskRelation> taskRelations) {
        long finishedCount = taskRelations.stream()
                .map(UserTaskRelation::getStatus)
                .filter(Status.FINISHED::isInstance)
                .count();
        // FIXME: 4/18/21
        return (double)finishedCount/(double)taskRelations.size();
    }

    private OptionalDouble getAverageTasksGrade(List<UserTaskRelation> taskRelations) {
        return taskRelations.stream()
                .map(UserTaskRelation::getGrade)
                .map(Grade::getIntValue)
                .mapToDouble(Integer::doubleValue)
                .average();
    }

    private Map<Grade, Long> getCountByTasksGrade(List<UserTaskRelation> taskRelations) {
        return taskRelations.stream()
                .map(UserTaskRelation::getGrade)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
