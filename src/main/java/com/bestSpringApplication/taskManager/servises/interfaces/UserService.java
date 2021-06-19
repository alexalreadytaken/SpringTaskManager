package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.entities.User;
import com.bestSpringApplication.taskManager.models.enums.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    void validateUserExistsAndContainsRoleOrThrow(String userId, Role role);

    void validateUserExistsOrThrow(String userId);

    List<User> getUsers();

    Optional<User> getUserById(String id);
}
