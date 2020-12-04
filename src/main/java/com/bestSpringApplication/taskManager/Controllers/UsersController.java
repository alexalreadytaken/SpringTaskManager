package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.model.user.UserModel;
import com.bestSpringApplication.taskManager.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<UserModel> userModelList(){
        return userService.getAllUsers();
    }


}
