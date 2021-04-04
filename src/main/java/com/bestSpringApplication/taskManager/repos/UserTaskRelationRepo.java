package com.bestSpringApplication.taskManager.repos;

import com.bestSpringApplication.taskManager.models.classes.UserTaskRelationImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTaskRelationRepo extends JpaRepository<UserTaskRelationImpl,Integer> {
    boolean existsBySchemaIdAndUserIdAndTaskId(String schemaKey, String studentId, String taskId);
    Optional<UserTaskRelationImpl> getBySchemaIdAndUserIdAndTaskId(String schemaKey, String studentId, String taskId);
    List<UserTaskRelationImpl> getAllBySchemaIdAndUserId(String schemaId,String studentId);
}
