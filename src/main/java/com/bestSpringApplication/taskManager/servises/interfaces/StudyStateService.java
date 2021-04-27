package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.enums.Status;

import java.util.List;

public interface StudyStateService {
    void prepareSchema(AbstractStudySchema schema, String userId);

    void prepareTask(AbstractStudySchema schema, AbstractTask task, String userId);

    void openTask(String schemaId, String userId, String taskId);

    void setStatusForUserTask(String schemaId, String userId, String taskId,Status status);

    void setGradeForUserTask(String schemaId, String userId, String taskId,Grade grade);

    void setStatusAndGradeForUserTask(String schemaId, String userId, String taskId,Status status,Grade grade);

    boolean taskExists(String schemaId, String userId, String taskId);

    boolean taskFinished(String schemaId,String userId,String taskId);

    boolean taskContainsStatus(String schemaId, String userId, String taskId, Status status);

    boolean taskInWork(String schemaId, String userId, String taskId);

    List<String> getCompletedTasksIdOfSchemaForUser(String schemaId, String userId);

    List<String> getOpenedSchemasIdOfUser(String userId);

    List<String> getOpenedTasksIdBySchemaOfUser(String userId, String schemaId);

    List<UserTaskRelation> getAllRelationsBySchemaId(String schemaId);

    List<UserTaskRelation> getSchemaStateByUserId(String userId, String schemaId);

    List<UserTaskRelation> getRelationsBySchemaIdAndTaskId(String schemaId, String taskId);
}