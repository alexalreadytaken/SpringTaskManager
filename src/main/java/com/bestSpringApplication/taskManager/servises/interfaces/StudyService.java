package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.enums.Status;

import java.util.List;
import java.util.Map;

public interface StudyService {
    void setSchemaToUser(String userId,String schemaId);

    void reopenTask(String schemaId, String userId, String taskId);

    boolean canStartTask(String schemaId, String userId, String taskId);

    void forceStartTask(String schemaId, String userId, String taskId);

    void startTaskWithValidation(String schemaId, String userId, String taskId);

    List<AbstractStudySchema> getUserSchemas(String userId);

    List<AbstractTask> getUserSchemasRootTasks(String userId);

    List<AbstractTask> getOpenedUserTasksOfSchema(String userId, String schemaId);
}
