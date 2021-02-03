package com.bestSpringApplication.taskManager.repos;

import com.bestSpringApplication.taskManager.models.idRelation.IdRelationImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdRelationRepo extends JpaRepository<IdRelationImpl,Integer> {
}
