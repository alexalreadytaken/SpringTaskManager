package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.enums.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void validateUserExistsAndContainsRoleOrThrow(String userId, Role role);

    void validateUserExistsOrThrow(String userId);


}
