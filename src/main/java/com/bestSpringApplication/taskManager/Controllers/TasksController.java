package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalXmlFormatException;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TasksSchema;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TasksController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);

    @Value("${task.pool.path}")
    private String taskPoolPath;

    private static final Set<String> confirmedFileTypes =
        Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "xml","mrp","txt")));


    /*@GetMapping("admin/tasks")
    public Flux<Object> filesList(){
        return Flux.from(new File(taskPoolPath));
    }*/
    @GetMapping("/admin/tasks")
    public List<Map<String,String>> fileTaskList() throws IOException {
        return Arrays.stream(
            new File(taskPoolPath).listFiles(el -> !el.isDirectory()))
            .map(el->new HashMap<String,String>(){{put("filename",el.getName());}})
            .collect(Collectors.toList());
    }

    @PostMapping("/admin/addTasks")
    @ResponseStatus(HttpStatus.OK)
    public void newTasks(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String[] fileNameAndType = Objects.requireNonNull(file.getOriginalFilename()).split("\\.", 2);
            if (confirmedFileTypes.contains(fileNameAndType[1])){
                Document courseXml = new SAXBuilder().build(file.getInputStream());
                TasksSchema.parseFromXml(courseXml);
                file.transferTo(new File(taskPoolPath+file.getOriginalFilename()));
            }else {
                LOGGER.warn("unsupported file type sent,file:{}",file.getOriginalFilename());
                throw new IllegalFileFormatException(String.format("файл с расширением %s не поддерживается",fileNameAndType[1]));
            }
        }catch (JDOMException ex){
            LOGGER.error("error with XML parse:{} file:{}",ex.getLocalizedMessage(),file.getOriginalFilename());
            throw new IllegalXmlFormatException("загрузка файла не удалась,проверьте структуру своего XML файла");
        }catch (NullPointerException ex){
            LOGGER.error("NPE with message:{}",ex.getMessage());
        }
    }
}

























































