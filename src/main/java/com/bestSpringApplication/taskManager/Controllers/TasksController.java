package com.bestSpringApplication.taskManager.Controllers;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
public class TasksController {

    private static final Set<String> confirmedFileTypes =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                            "xml","mrp")));


    @PostMapping("/admin/addTasks")
    public Map<String,Boolean> newTasks(@RequestParam("file") MultipartFile file){
        Map<String,Boolean> response = new HashMap<>();
        System.out.println(file.getName());
        if (confirmedFileTypes.contains(file.getOriginalFilename().split("\\.")[1])){
            response.put("success",true);
        }else {
            response.put("success",false);
        }
        return response;
    }
}
