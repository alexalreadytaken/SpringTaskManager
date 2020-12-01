package com.bestSpringApplication.taskManager.Controllers;

import com.bestSpringApplication.taskManager.model.user.UserModel;
import com.bestSpringApplication.taskManager.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {


    private final UserService userService;

    @Autowired
    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginReturn(){
        return "login";
    }
    @GetMapping("/register")
    public String returnRegister(Model model){
        model.addAttribute("UserModel",new UserModel());
        return "register";
    }
    @GetMapping("/home")
    public String homeReturn(){
        return "home";
    }
    @GetMapping("")
    public String home0(){
        return "redirect:/home";
    }
    @GetMapping("/admin")
    public String adminPageReturn(){
        return "admin";
    }

    @PostMapping("/register")
    public String registerUser(UserModel userModel){
        if (userService.containsMail(userModel.getMail())){
            return "redirect:/error";
        }else {
            userService.saveUser(userModel);
            return "redirect:/login";
        }
    }

}
