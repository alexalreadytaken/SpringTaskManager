package com.bestSpringApplication.taskManager.Controllers;

import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.models.user.User;
import com.bestSpringApplication.taskManager.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UsersHandler {

    @Autowired
    private UserService userService;

    public static User existUser = new User("user","user","user", Role.STUDENT);
    public static User existAdmin = new User("admin","admin","admin",Role.ADMIN);
    public static User notExistUser = new User("a","b","c",Role.STUDENT);

    public void prepare() {
        try {
            userService.deleteUser((User) userService.loadUserByUsername(existUser.getName()));
            userService.deleteUser((User) userService.loadUserByUsername(existAdmin.getName()));
            userService.deleteUser((User) userService.loadUserByUsername(notExistUser.getName()));
        }catch (UsernameNotFoundException ignored){}
        try {
            userService.saveUser((User) existUser.clone());
            userService.saveUser((User) existAdmin.clone());
        }catch (CloneNotSupportedException ignored){}
    }

    public void curlUp(){
        try {
            userService.deleteUser((User) userService.loadUserByUsername(existUser.getName()));
            userService.deleteUser((User) userService.loadUserByUsername(existAdmin.getName()));
            userService.deleteUser((User) userService.loadUserByUsername(notExistUser.getName()));
        }catch (UsernameNotFoundException ignored){}
    }

}
