package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.ContentNotFoundException;
import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalXmlFormatException;
import com.bestSpringApplication.taskManager.handlers.jsonView.SchemasView;
import com.bestSpringApplication.taskManager.models.idRelation.IdRelation;
import com.bestSpringApplication.taskManager.models.study.enums.Grade;
import com.bestSpringApplication.taskManager.models.study.implementations.DependencyImpl;
import com.bestSpringApplication.taskManager.models.study.implementations.StudySchemeImpl;
import com.bestSpringApplication.taskManager.models.study.implementations.UserTaskRelationImpl;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudyScheme;
import com.bestSpringApplication.taskManager.repos.IdRelationRepo;
import com.bestSpringApplication.taskManager.servises.UserTaskRelationService;
import com.fasterxml.jackson.annotation.JsonView;
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

@RestController
@RequestMapping("/admin/schemas")
@CrossOrigin
public class SchemasController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemasController.class);

    @Value("${task.pool.path}")
    private String taskPoolPath;

    private final UserTaskRelationService utrService;
    private final IdRelationRepo idRelationRepo;

    public Map<Integer,StudyScheme> schemas;
    private List<Dependency> schemasDependencies = Collections.singletonList(
            new DependencyImpl("0", "1"));
    private int schemesCount;

    private static final Set<String> confirmedFileTypes =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    "xml","mrp","txt")));

    @Autowired
    public SchemasController(UserTaskRelationService utrService, IdRelationRepo idRelationRepo) {
        this.utrService = utrService;
        this.idRelationRepo = idRelationRepo;
    }

    @GetMapping("/test/{some}")
    public UserTaskRelationImpl some(@PathVariable int some){
        return utrService.getRelationById(some)
            .orElseThrow(()->new ContentNotFoundException("relation not found"));
    }

    @PostConstruct
    public void init(){
        IdRelation idRelation = new IdRelation("sowjdo", "xswojdw");
        IdRelation tgr = new IdRelation("sjytjytjowjdo", "xsfrfewojdw");
        UserTaskRelationImpl userTaskRelation = new UserTaskRelationImpl(idRelation, tgr, true, true, Grade.FOUR);

        utrService.saveRelation(userTaskRelation);

        schemas = new HashMap<>();
        schemesCount = 0;
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
                            StudySchemeImpl schema = StudySchemeImpl.parseFromXml(schemaDoc);
                            //fixme
                            schema.setName(fileName);
                            schema.setId(String.valueOf(schemesCount));
                            LOGGER.trace("putting schema to schemes,file:{}", fileName);
                            schemas.put(schemesCount++,schema);
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
            tasksDir.mkdir();
        }
    }

    //fixme
    @GetMapping
    @JsonView(SchemasView.OverviewInfo.class)
    public Map<String,Object> schemasMap(){
        Map<String,Object> schemasAndDependencies = new HashMap<>();
        schemasAndDependencies.put("values",schemas);
        schemasAndDependencies.put("dependencies",schemasDependencies);
        return schemasAndDependencies;
    }

    @GetMapping("/{id}")
    public StudyScheme schemeDetails(@PathVariable String id) {
        String notFoundResponse = String.format("Схема с id=%s не найдена", id);
        try {
            int id0 = Integer.parseInt(id);
            return Optional.ofNullable(schemas.get(id0))
                    .orElseThrow(()->
                            new ContentNotFoundException(notFoundResponse));
        }catch (NumberFormatException ex){
            throw new ContentNotFoundException(notFoundResponse);
        }
    }

    @GetMapping("/files")
    public List<String> schemasFileList() {
        File file = new File(taskPoolPath);
        Optional<File[]> filesOpt = Optional.ofNullable(file.listFiles(File::isFile));
        List<String> fileNames = new ArrayList<>();
        filesOpt.ifPresent(files-> Arrays.stream(files).map(File::getName).forEach(fileNames::add));
        return fileNames;
    }

    @PostMapping("/add/dependency")
    @ResponseStatus(HttpStatus.OK)
    public void newSchemasDependency(@RequestBody Map<String,String> schemasId){
        String parentId = schemasId.get("parentId");
        String childId = schemasId.get("childId");
        schemasDependencies.add(new DependencyImpl(parentId,childId));
    }

    @PostMapping("/add")
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

