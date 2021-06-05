package com.bestSpringApplication.taskManager.repos;

import com.bestSpringApplication.taskManager.models.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group,Long> {
}
