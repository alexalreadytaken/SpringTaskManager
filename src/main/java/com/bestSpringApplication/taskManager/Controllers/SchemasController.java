package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalXmlFormatException;
import com.bestSpringApplication.taskManager.models.Study.implementations.StudySchemeImpl;
import com.bestSpringApplication.taskManager.models.Study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.Study.interfaces.StudyScheme;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/admin/schemas/")
public class SchemasController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemasController.class);

    @Value("${task.pool.path}")
    private String taskPoolPath;

    public Map<Integer,StudyScheme> schemas;
    private List<Dependency> schemasDependencies;
    private int schemesCount;
    private List<String> fileNames;

    private static final Set<String> confirmedFileTypes =
        Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "xml","mrp","txt")));

    @PostConstruct
    public void init(){
        schemasDependencies = new ArrayList<>();
        fileNames = new ArrayList<>();
        schemas = new HashMap<>();
        schemesCount = 0;
        File tasksDir = new File(taskPoolPath);
        if (tasksDir.exists()){
            Optional<File[]> files =
                Optional.ofNullable(tasksDir.listFiles(el -> !el.isDirectory()));
            files.ifPresent(files0 ->
                Arrays.stream(files0).forEach(file -> {
                    try {
                        LOGGER.trace("getting file {} to parse",file.getName());
                        InputStream fileInputStream = new FileInputStream(file);
                        Document schemaDoc = new SAXBuilder().build(fileInputStream);
                        StudyScheme schema = StudySchemeImpl.parseFromXml(schemaDoc);
                        LOGGER.trace("putting schema to schemes,file:{}",file.getName());
                        schemas.put(schemesCount++,schema);
                        fileNames.add(file.getName());
                    } catch (FileNotFoundException e) {
                        LOGGER.warn("file was deleted in initializing time");
                    } catch (JDOMException e) {
                        LOGGER.error("error with parse XML:{},file:{}",e.getMessage(),file.getName());
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                    }
                })
            );
        }else {
            tasksDir.mkdir();
        }
    }

    @GetMapping
    public Map<Integer,StudyScheme> schemasMap(){
        return schemas;
    }
    @GetMapping("/tasks")
    public StudyScheme g(){
        return schemas.get(0);
    }

    @GetMapping("files")
    public List<String> schemasFileList() {
        return fileNames;
    }

    @PostMapping("add/dependency")
    @ResponseStatus(HttpStatus.OK)
    public void newSchemasDependency(@RequestBody Map<String,String> schemasId){
        //todo
    }

    @PostMapping("add")
    @ResponseStatus(HttpStatus.OK)
    public void newScheme(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String[] fileNameAndType = Objects.requireNonNull(file.getOriginalFilename()).split("\\.", 2);
            LOGGER.trace("Receive file:{}",file.getOriginalFilename());
            if (confirmedFileTypes.contains(fileNameAndType[1])){
                Document courseXml = new SAXBuilder().build(file.getInputStream());
                StudySchemeImpl.parseFromXml(courseXml);
                LOGGER.trace("Move file {} to directory {}",
                    file.getOriginalFilename(),taskPoolPath);
                file.transferTo(new File(taskPoolPath+file.getOriginalFilename()));
                fileNames.add(file.getOriginalFilename());
            }else {
                LOGGER.warn("unsupported file type sent,file:{}",file.getOriginalFilename());
                throw new IllegalFileFormatException(String.format("файл с расширением %s не поддерживается",fileNameAndType[1]));
            }
        }catch (JDOMException ex){
            LOGGER.error("error with XML parse:{} file:{}",ex.getLocalizedMessage(),file.getOriginalFilename());
            throw new IllegalXmlFormatException("загрузка файла не удалась,проверьте структуру своего XML файла");
        }
    }
}

























































