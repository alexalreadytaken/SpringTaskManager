package com.bestSpringApplication.taskManager.repos;

import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTaskRelationRepo extends JpaRepository<UserTaskRelation,Integer> {
    boolean existsBySchemaIdAndUserIdAndTaskId(String schemaId, String studentId, String taskId);

    List<UserTaskRelation> getAllBySchemaIdAndUserId(String schemaId, String studentId);

    List<UserTaskRelation> getAllBySchemaId(String schemaId);

    @Query(value = "select distinct schemaId from user_task_relation where userId=userId")
    List<String> getOpenedSchemasIdOfUser(String userId);

    // TODO: 4/15/21 how name
    String tasksBySchemeIdAndUserIdQuery = "select taskId from user_task_relation where userId=userId and schemaId=schemaId ";

    @Query(value = tasksBySchemeIdAndUserIdQuery+"and status='in_work'")
    List<String> getOpenedTasksIdBySchemaIdAndUserId(String userId, String schemaId);

    @Query(value = tasksBySchemeIdAndUserIdQuery+"and status='finished' and grade>=3")
    List<String> getCompletedTasksIdBySchemaIdAndUserId(String userId, String schemaId);
}
