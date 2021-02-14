package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.ContentNotFoundException;
import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalXmlFormatException;
import com.bestSpringApplication.taskManager.handlers.jsonView.SchemasView;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.models.study.implementations.StudySchemeImpl;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudyScheme;
import com.bestSpringApplication.taskManager.models.user.User;
import com.bestSpringApplication.taskManager.servises.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/schemas")
@CrossOrigin
public class SchemasController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemasController.class);

    @Value("${task.pool.path}")
    private String taskPoolPath;

    public Map<String, StudyScheme> masterSchemas;
    public Map<String ,Map<String,StudyScheme>> clonedSchemas;

    private int masterSchemasCount;

    private static final Set<String> confirmedFileTypes =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    "xml","mrp","txt")));

    @PostConstruct
    public void init(){
        masterSchemas = new HashMap<>();
        clonedSchemas = new HashMap<>();
        masterSchemasCount = 0;
        File tasksDir = new File(taskPoolPath);
        if (tasksDir.exists()){
            Optional<File[]> files =
                    Optional.ofNullable(tasksDir.listFiles(el -> !el.isDirectory()));
            files.ifPresent(files0 ->
                    Arrays.stream(files0).forEach(file -> {
                        String fileName = file.getName();
                        try {
                            LOGGER.trace("getting file {} to parse", fileName);
                            InputStream fileInputStream = new FileInputStream(file);
                            Document schemaDoc = new SAXBuilder().build(fileInputStream);
                            StudySchemeImpl schema = (StudySchemeImpl) StudySchemeImpl.parseFromXml(schemaDoc);
                            //fixme
                            schema.setName(fileName);
                            String id = String.valueOf(masterSchemasCount++);
                            schema.setId(id);
                            LOGGER.trace("putting schema to Schemas,file:{}", fileName);
                            masterSchemas.put(id,schema);
                        } catch (FileNotFoundException e) {
                            LOGGER.warn("file {} was deleted in initializing time",file);
                        } catch (JDOMException e) {
                            LOGGER.error("error with parse XML:{},file:{}",e.getMessage(), fileName);
                        } catch (IOException e) {
                            LOGGER.error(e.getMessage());
                        }
                    })
            );
        }else {
            LOGGER.trace("directory {} not found, creating...",taskPoolPath);
            tasksDir.mkdir();
        }
    }

    @GetMapping("/master")
    @JsonView(SchemasView.OverviewInfo.class)
    public Collection<StudyScheme> masterSchemas(){
        return masterSchemas.values();
    }

    @GetMapping("/master/{schemaId}")
    public StudyScheme masterSchemeById(@PathVariable String schemaId){
        return Optional.ofNullable(masterSchemas.get(schemaId))
                .orElseThrow(()-> new ContentNotFoundException("Курс не найден"));
    }

    @GetMapping("master/files")
    public List<String> schemasFileList() {
        File file = new File(taskPoolPath);
        Optional<File[]> filesOpt = Optional.ofNullable(file.listFiles(File::isFile));
        List<String> fileNames = new ArrayList<>();
        filesOpt.ifPresent(files-> Arrays.stream(files).map(File::getName).forEach(fileNames::add));
        return fileNames;
    }

    // TODO: 2/11/2021 upload permission by role
    @PostMapping("/master/add")
    @ResponseStatus(HttpStatus.OK)
    public void newScheme(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String[] fileNameAndType = Objects.requireNonNull(file.getOriginalFilename()).split("\\.", 2);
            LOGGER.trace("Receive file:{}",file.getOriginalFilename());
            if (confirmedFileTypes.contains(fileNameAndType[1])){
                Document courseXml = new SAXBuilder().build(file.getInputStream());
                StudyScheme studyScheme = StudySchemeImpl.parseFromXml(courseXml);

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
        }
    }

}

