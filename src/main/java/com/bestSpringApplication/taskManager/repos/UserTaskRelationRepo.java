package com.bestSpringApplication.taskManager.repos;

import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTaskRelationRepo extends JpaRepository<UserTaskRelation,Integer> {
    boolean existsBySchemaIdAndUserIdAndTaskId(String schemaId, String studentId, String taskId);
    List<UserTaskRelation> getAllBySchemaIdAndUserId(String schemaId, String studentId);

    @Query(value = "select distinct schemaId from user_task_relation where userId=userId")
    List<String> getAllOpenedSchemasIdToStudent(String userId);

    @Query(value = "select taskId from user_task_relation where userId=userId and schemaId=schemaId")
    List<String> getOpenedTasksIdBySchemaOfStudent(String userId,String schemaId);
}
