package com.bestSpringApplication.taskManager.repos;

import com.bestSpringApplication.taskManager.models.user.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByMail(String mail);
}
