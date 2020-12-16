package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.handlers.parsers.xml.TasksSchemaParser;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TasksSchema;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
public class TasksController {

    @Value("${task.pool.path}")
    private String taskPoolPath;

    private static final Set<String> confirmedFileTypes =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    "xml","mrp","txt")));


    @PostMapping("/admin/addTasks")
    public TasksSchema newTasks(@RequestParam("file") MultipartFile file) throws IOException, JDOMException{
        String[] fileName = file.getOriginalFilename().split("\\.", 2);
        if (confirmedFileTypes.contains(fileName[1])){
            Document courseXml = new SAXBuilder().build(file.getInputStream());
            file.transferTo(new File(taskPoolPath+file.getOriginalFilename()));
            return TasksSchema.parseFromXml(courseXml);
        }else {
            throw new IllegalFileFormatException(String.format("файл с расширением %s не поддерживается",fileName[1]));
        }
    }
}

























































