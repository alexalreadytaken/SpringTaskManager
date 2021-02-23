package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.forClient.ContentNotFoundException;
import com.bestSpringApplication.taskManager.handlers.exceptions.forClient.EmailExistsException;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.models.user.User;
import com.bestSpringApplication.taskManager.servises.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UsersController{

    @NonNull
    private final UserService userService;

    private final String REGISTER_MAPPING = "/register/new";

    private final String USERS_LIST_MAPPING = "/admin/users";
    private final String USER_BY_ID_MAPPING = "/admin/users/{id}";


    @PostMapping(REGISTER_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    public void register(@RequestBody Map<String,String> body){
        if (userService.containsMail(body.get("mail"))){
            throw new EmailExistsException("Пользователь с такой почтой уже существует");
        }else{
            User user = User.builder()
                    .mail(body.get("mail"))
                    .name(body.get("name"))
                    .password(body.get("password"))
                    .role(Role.STUDENT)
                    .build();
            userService.saveUser(user);
        }
    }

    @GetMapping(USERS_LIST_MAPPING)
    public List<User> userList(){
        return userService.getAllUsers();
    }

    @GetMapping(USER_BY_ID_MAPPING)
    public User user(@PathVariable String id){
        return findUserById(id);
    }

    @DeleteMapping(USER_BY_ID_MAPPING)
    public Map<String,Boolean> deleteUser(@PathVariable String id){
        //fixme
        Map<String,Boolean> response = new HashMap<>();
        try {
            User user = findUserById(id);
            userService.deleteUser(user);
            response.put("delete",true);
        }catch (ContentNotFoundException ex){
            response.put("delete",false);
        }
        return response;
    }

    private User findUserById(String id){
        return userService.getUserById(id).orElseThrow(
            ()->new ContentNotFoundException(
                String.format("user with id=%s not found",id)));
    }
}








