package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.ContentNotFoundException;
import com.bestSpringApplication.taskManager.models.user.User;
import com.bestSpringApplication.taskManager.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }
    private static final String methodistIp = "http://10.1.0.88:8080";

    @PostMapping("/reg")
    public Map<String, Boolean> register(@RequestBody Map<String,String> body){
        HashMap<String, Boolean> response = new HashMap<>();

        if (userService.containsMail(body.get("mail"))){
            response.put("register",false);
        }else {
            String mail = body.get("mail");
            String name = body.get("name");
            String password = body.get("password");
            userService.saveUser(new User(mail,name,password, "USER"));
            response.put("register",true);
        }
        return response;
    }
    @GetMapping("/admin/users")
    public List<User> userList(){
        return userService.getAllUsers();
    }

    @GetMapping("/admin/users/{id}")
    public User user(@PathVariable String id){
        return findUserById(id);
    }

    @DeleteMapping("/admin/users/{id}")
    public Map<String,Boolean> deleteUser(@PathVariable String id){
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








