package com.bestSpringApplication.taskManager.repos;

import com.bestSpringApplication.taskManager.models.classes.UserTaskRelationImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserTaskRelationRepo extends JpaRepository<UserTaskRelationImpl,Integer> {
    boolean existsBySchemaIdAndUserIdAndTaskId(String schemaId, String studentId, String taskId);
    Optional<UserTaskRelationImpl> getBySchemaIdAndUserIdAndTaskId(String schemaId, String studentId, String taskId);
    List<UserTaskRelationImpl> getAllBySchemaIdAndUserId(String schemaId,String studentId);

    @Query(value = "select distinct schemaId from user_task_relation where userId=userId")
    List<String> getAllOpenedSchemasIdToStudent(String userId);

    @Query(value = "select taskId from user_task_relation where userId=userId and schemaId=schemaId")
    List<String> getOpenedTasksIdBySchemaOfStudent(String userId,String schemaId);
}
