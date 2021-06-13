package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;

import java.util.List;

public interface StudyService {
    void setSchemaToUser(String userId,String schemaId);

    void setSchemaToGroup(String groupId,String schemaId);

    void reopenTask(String schemaId, String userId, String taskId);

    boolean canStartTask(String schemaId, String userId, String taskId);

    void forceStartTask(String schemaId, String userId, String taskId);

    void startTaskWithValidation(String schemaId, String userId, String taskId);

    List<AbstractTask> getAvailableToStartUserTasks(String userId);

    List<AbstractTask> getAvailableToStartUserTasks(String userId, String schemaId);

    List<AbstractTask> getUserSchemasRootTasks(String userId);

    List<AbstractTask> getOpenedUserTasks(String userId, String schemaId);

    List<AbstractTask> getOpenedUserTasks(String userId);
}
