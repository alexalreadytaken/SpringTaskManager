package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.model.user.UserModel;
import com.bestSpringApplication.taskManager.servises.UserService;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.http.HTTPException;
import java.util.*;

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
    public UserModel user(@PathVariable Long id) {
        return userService.getUserById(id).orElse(null);//todo not found exception,PASHA?
    }
}
