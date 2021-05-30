package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.classes.Summary;

import java.util.List;

public interface SummaryProvider {
    List<Summary> getTasksSummaryBySchema(String schemaId);

    Summary getSummaryBySchemaIdAndTaskId(String schemaId, String taskId);

    Summary getUserSchemaSummary(String schemaId,String userId);
}
