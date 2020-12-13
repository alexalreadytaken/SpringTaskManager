package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.ContentNotFoundException;
import com.bestSpringApplication.taskManager.models.user.UserModel;
import com.bestSpringApplication.taskManager.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping("/reg")
    public Map<String, Boolean> register(@RequestBody Map<String,String> body){
        HashMap<String, Boolean> response = new HashMap<>();

        if (userService.containsMail(body.get("mail"))){
            response.put("register",false);
        }else {
            String mail = body.get("mail");
            String name = body.get("name");
            String password = body.get("password");
            userService.saveUser(new UserModel(mail,name,password, "USER"));
            response.put("register",true);
        }
        return response;
    }
    @GetMapping("/admin/users")
    public List<UserModel> userList(){
        List<UserModel> allUsers = userService.getAllUsers();
        allUsers.forEach(usr->usr.setPassword("not access"));
        return allUsers;
    }
    @GetMapping("/admin/users/{id}")
    public UserModel user(@PathVariable String id){
        UserModel userById = findUserById(id);
        userById.setPassword("no access");
        return userById;
    }

    @DeleteMapping("/admin/users/{id}")
    public Map<String,Boolean> deleteUser(@PathVariable String id){
        Map<String,Boolean> response = new HashMap<>();
        try {
            UserModel user = findUserById(id);
            userService.deleteUser(user);
            response.put("delete",true);
        }catch (ContentNotFoundException ex){
            response.put("delete",false);
        }
        return response;
    }
    private UserModel findUserById(String id){
        return userService.getUserById(id).orElseThrow(
            ()->new ContentNotFoundException(
                String.format("user with id=%s not found",id)));
    }
}








