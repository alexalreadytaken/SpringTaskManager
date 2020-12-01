package com.bestSpringApplication.taskManager.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginReturn(){
        return "login";
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
    @GetMapping("/register")
    public String returnRegister(){
        return "register";
    }

}
