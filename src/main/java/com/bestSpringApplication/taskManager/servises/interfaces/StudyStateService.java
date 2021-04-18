package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;

import java.util.List;

public interface StudyStateService {
    void prepareSchema(AbstractStudySchema schema, String userId);

    void prepareTask(AbstractStudySchema schema, AbstractTask task, String userId);

    void openTask(String schemaId, String userId, String taskId);

    boolean taskExists(String schemaId, String userId, String taskId);

    boolean taskInWork(String schemaId, String userId, String taskId);

    List<String> getCompletedTasksIdOfSchemaForUser(String schemaId, String userId);

    List<String> getOpenedSchemasIdOfUser(String userId);

    List<String> getOpenedTasksIdBySchemaOfUser(String userId, String schemaId);

    List<UserTaskRelation> getAllRelationsBySchemaId(String schemaId);

    List<UserTaskRelation> getSchemaStateByUserId(String userId, String schemaId);

    List<UserTaskRelation> getRelationsBySchemaIdAndTaskId(String schemaId, String taskId);
}
