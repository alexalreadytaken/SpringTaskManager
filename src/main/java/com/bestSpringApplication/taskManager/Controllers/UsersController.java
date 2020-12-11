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
import java.util.function.Supplier;

@RestController
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping("/reg")
    public Map<String, String> register(@RequestBody Map<String,String> body){
        HashMap<String, String> response = new HashMap<>();

        if (userService.containsMail(body.get("mail"))){
            response.put("register","false");
        }else {
            String mail = body.get("mail");
            String name = body.get("name");
            String password = body.get("password");
            userService.saveUser(new UserModel(mail,name,password, "USER"));
            response.put("register","true");
        }
        return response;
    }
    @GetMapping("/admin/users")
    public List<Map<String,String>> userList(){
        List<Map<String,String>> mainUsersInform = new ArrayList<>();
        userService.getAllUsers().forEach(user->
            mainUsersInform.add(new HashMap<String, String>(){{
                put("id",user.getId().toString());
                put("name",user.getName());
            }})
        );
        return mainUsersInform;
    }
    @GetMapping("/admin/users/{id}")
    public UserModel user(@PathVariable String id) throws Throwable {
        return userService.getUserById(id).orElseThrow(
            (Supplier<Throwable>) () -> new ContentNotFoundException(
                String.format("user with id=%s not found",id)));
    }
}
