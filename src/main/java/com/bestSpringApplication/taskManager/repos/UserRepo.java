package com.bestSpringApplication.taskManager.repos;

import com.bestSpringApplication.taskManager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByMail(String mail);
}
