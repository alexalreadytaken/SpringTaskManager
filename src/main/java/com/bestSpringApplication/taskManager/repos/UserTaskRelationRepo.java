package com.bestSpringApplication.taskManager.repos;

import com.bestSpringApplication.taskManager.models.study.classes.UserTaskRelationImpl;
import com.bestSpringApplication.taskManager.models.study.interfaces.UserTaskRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTaskRelationRepo extends JpaRepository<UserTaskRelationImpl,Integer> {
    boolean existsBySchemaIdAndUserIdAndTaskId(String schemaKey, String studentId, String taskId);
    Optional<UserTaskRelation> getBySchemaIdAndUserIdAndTaskId(String schemaKey, String studentId, String taskId);
}
