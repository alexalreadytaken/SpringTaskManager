package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import com.bestSpringApplication.taskManager.models.enums.Grade;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

public interface SummaryHandler {
    double getPercentCompleteTasks(List<UserTaskRelation> taskRelations);

    OptionalDouble getAverageTasksGrade(List<UserTaskRelation> taskRelations);

    Map<Grade, Long> getCountByTasksGrade(List<UserTaskRelation> taskRelations);
}
