package com.bestSpringApplication.taskManager.repos;

import com.bestSpringApplication.taskManager.models.study.classes.UserTaskRelationImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTaskRelationRepo extends JpaRepository<UserTaskRelationImpl,Integer> {
    boolean existsBySchemeIdAndUserIdAndTaskId(String schemeId,String userId,String taskId);
}