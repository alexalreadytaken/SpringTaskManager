package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.ContentNotFoundException;
import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalXmlFormatException;
import com.bestSpringApplication.taskManager.models.study.implementations.StudySchemaImpl;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import com.bestSpringApplication.taskManager.servises.UserService;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schemas")
@CrossOrigin
public class SchemasController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemasController.class);

    @Value("${task.pool.path}")
    private String taskPoolPath;

    public Map<String, StudySchema> masterSchemas;
    public Map<String ,Map<String, StudySchema>> clonedSchemas;

    private static final Set<String> confirmedFileTypes =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    "xml","mrp","txt")));

    private UserService userService;

    @Autowired
    public SchemasController(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init(){
        masterSchemas = new HashMap<>();
        clonedSchemas = new HashMap<>();
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
                            StudySchema schemaFromDir = StudySchemaImpl.parseFromXml(schemaDoc);
                            LOGGER.trace("putting schema to Schemas,file:{}", fileName);
                            putToMasterSchemas(schemaFromDir);
                        } catch (FileNotFoundException e) {
                            LOGGER.warn("file {} was deleted in initializing time",fileName);
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

    @GetMapping("/master/{schemaKey}")
    public StudySchema masterSchemaById(@PathVariable String schemaKey){
        return getMasterSchemaById(schemaKey);
    }


    // TODO: 2/15/2021
    @GetMapping("/master/{schemaKey}/addTo/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public void addSchemaToStudent(@PathVariable String schemaKey, @PathVariable String studentId){
//        StudySchema masterSchema = getMasterSchemaById(schemaKey);
//
//        if(!userService.existsUserById(studentId))throw new ContentNotFoundException("Студент не найден");
    }

    @PostMapping("/master/files/add")
    @ResponseStatus(HttpStatus.OK)
    public void newSchema(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String[] fileNameAndType = Objects.requireNonNull(file.getOriginalFilename()).split("\\.", 2);
            LOGGER.trace("Receive file:{}",file.getOriginalFilename());
            if (confirmedFileTypes.contains(fileNameAndType[1])){
                Document courseXml = new SAXBuilder().build(file.getInputStream());
                StudySchema studySchema = StudySchemaImpl.parseFromXml(courseXml);
                putToMasterSchemas(studySchema);
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

    @GetMapping("/master")
    public List<Task> masterSchemasOverview(){
        return masterSchemas
                .values()
                .stream()
                .map(StudySchema::getRootTask)
                .collect(Collectors.toList());
    }

    @GetMapping("master/files")
    public List<String> schemasFileList() {
        File file = new File(taskPoolPath);
        Optional<File[]> filesOpt = Optional.ofNullable(file.listFiles(File::isFile));
        List<String> fileNames = new ArrayList<>();
        filesOpt.ifPresent(files-> Arrays.stream(files).map(File::getName).forEach(fileNames::add));
        return fileNames;
    }

    private void putToMasterSchemas(StudySchema studySchema){
        String key = studySchema.getRootTask().getName();
        masterSchemas.put(key,studySchema);
    }

    // FIXME: 2/15/2021 return optional or throw ?
    private StudySchema getMasterSchemaById(String schemaKey) {
        return Optional.ofNullable(masterSchemas.get(schemaKey))
                .orElseThrow(()-> new ContentNotFoundException("Курс не найден"));
    }
}

