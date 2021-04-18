package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.classes.GroupTaskSummary;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;

import java.util.List;

public interface SummaryProvider {
    List<GroupTaskSummary> getTasksSummaryBySchema(String schemaId);

    GroupTaskSummary getSummaryBySchemaIdAndTaskId(String schemaId,String taskId);

    List<UserTaskRelation> getUserTasksSummary(String schemaId, String userId);
}
