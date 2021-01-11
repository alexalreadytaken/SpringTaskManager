package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalXmlFormatException;
import com.bestSpringApplication.taskManager.models.user.User;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TasksSchema;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TasksController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);

    public static final Map<String,TasksSchema> SCHEMAS = new HashMap<>();

    @Value("${task.pool.path}")
    private String taskPoolPath;

    private static final Set<String> confirmedFileTypes =
        Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "xml","mrp","txt")));

    @PostConstruct
    public void init(){
        File tasksDir = new File(taskPoolPath);
        if (tasksDir.exists()){
            Optional<File[]> files =
                Optional.ofNullable(tasksDir.listFiles(el -> !el.isDirectory()));
            files.ifPresent(files1 ->
                Arrays.stream(files1).forEach(el-> {
                    try {
                        LOGGER.trace("getting file {} to parse",el.getName());
                        InputStream fileInputStream = new FileInputStream(el);
                        Document schemaDoc = new SAXBuilder().build(fileInputStream);
                        TasksSchema schema = TasksSchema.parseFromXml(schemaDoc);
                        LOGGER.trace("putting schema to schemes,file:{}",el.getName());
                        SCHEMAS.put(el.getName(),schema);
                    } catch (FileNotFoundException e) {
                        LOGGER.warn("file was deleted in initializing time");
                    } catch (JDOMException e) {
                        LOGGER.error("error with parse XML:{},file:{}",e.getMessage(),el.getName());
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                    }
                })
            );
        }else {
            tasksDir.mkdir();
        }
    }
    @GetMapping("/admin/tasks")
    public List<TasksSchema> schema(){
        return new ArrayList<>(SCHEMAS.values());
    }

    @GetMapping("/admin/tasksFiles")
    public List<Map<String,String>> fileTaskList() {
        return Arrays.stream(
            // doesn't really throw ?
            new File(taskPoolPath).listFiles(el -> !el.isDirectory()))
            .map(el->new HashMap<String,String>(){{put("filename",el.getName());}})
            .collect(Collectors.toList());
    }

    @PostMapping("/admin/addTasks")
    @ResponseStatus(HttpStatus.OK)
    public void newTasks(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String[] fileNameAndType = Objects.requireNonNull(file.getOriginalFilename()).split("\\.", 2);
            LOGGER.trace("Receive file:{}",file.getOriginalFilename());
            if (confirmedFileTypes.contains(fileNameAndType[1])){
                Document courseXml = new SAXBuilder().build(file.getInputStream());
                TasksSchema.parseFromXml(courseXml);
                LOGGER.trace("Move file {} to directory {}",
                    file.getOriginalFilename(),taskPoolPath);
                file.transferTo(new File(taskPoolPath+file.getOriginalFilename()));
            }else {
                LOGGER.warn("unsupported file type sent,file:{}",file.getOriginalFilename());
                throw new IllegalFileFormatException(String.format("файл с расширением %s не поддерживается",fileNameAndType[1]));
            }
        }catch (JDOMException ex){
            LOGGER.error("error with XML parse:{} file:{}",ex.getLocalizedMessage(),file.getOriginalFilename());
            throw new IllegalXmlFormatException("загрузка файла не удалась,проверьте структуру своего XML файла");
        }catch (NullPointerException ex){
            //fixme
            LOGGER.error("NPE with message:{}",ex.getMessage());
        }
    }
}

























































