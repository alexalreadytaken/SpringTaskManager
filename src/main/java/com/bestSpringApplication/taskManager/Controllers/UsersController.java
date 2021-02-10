package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.ContentNotFoundException;
import com.bestSpringApplication.taskManager.handlers.exceptions.EmailExistsException;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.models.user.User;
import com.bestSpringApplication.taskManager.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/register/new")
    @ResponseStatus(HttpStatus.OK)
    public void register(@RequestBody Map<String,String> body){
        if (userService.containsMail(body.get("mail"))){
            throw new EmailExistsException("Пользователь с такой почтой уже существует");
        }else {
            String mail = body.get("mail");
            String name = body.get("name");
            String password = body.get("password");
            userService.saveUser(new User(mail,name,password, Role.STUDENT));
        }
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








