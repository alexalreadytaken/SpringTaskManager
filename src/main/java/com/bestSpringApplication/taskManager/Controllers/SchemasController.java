package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import com.bestSpringApplication.taskManager.servises.MasterSchemasService;
import com.bestSpringApplication.taskManager.servises.StudentSchemasService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/schemas")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class SchemasController {

    private final String MASTER_SCHEMAS_MAPPING=                "/master";
    private final String MASTER_FILES_MAPPING =                 "/master/files";
    private final String MASTER_FILES_ADD_MAPPING =             "/master/files/add";
    private final String MASTER_SCHEMA_BY_KEY_MAPPING =         "/master/{schemaKey}";
    private final String ADD_MASTER_SCHEMA_TO_STUDENT_MAPPING = "/master/{schemaKey}/addTo/{studentId}";

    private final String STUDENT_SCHEMAS_MAPPING = "/student/{studentId}";


    @NonNull private final MasterSchemasService masterSchemasService;
    @NonNull private final StudentSchemasService studentSchemasService;

    private static final Set<String> confirmedFileTypes =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    "xml","mrp","txt")));

    @GetMapping(MASTER_SCHEMA_BY_KEY_MAPPING)
    public StudySchema masterSchemaByKey(@PathVariable String schemaKey){
        return masterSchemasService.schemaByKey(schemaKey);
    }

    @GetMapping(ADD_MASTER_SCHEMA_TO_STUDENT_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    public void addSchemaToStudent(@PathVariable String schemaKey, @PathVariable String studentId){
        log.trace("new relation student to schema;schemaKey = {},studentId = {} ",schemaKey,studentId);
        studentSchemasService.setSchemaToStudent(studentId,schemaKey);
    }

    @PostMapping(MASTER_FILES_ADD_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    public void newSchema(@RequestParam("file") MultipartFile file){
        String[] fileNameAndType = Objects.requireNonNull(file.getOriginalFilename()).split("\\.", 2);
        log.trace("Receive file:{}", file.getOriginalFilename());
        if (confirmedFileTypes.contains(fileNameAndType[1])) {
            masterSchemasService.putAndSaveFile(file);
        } else {
            log.warn("unsupported file type sent,file:{}", file.getOriginalFilename());
            throw new IllegalFileFormatException(String.format("файл с расширением %s не поддерживается", fileNameAndType[1]));
        }
    }

    @GetMapping(STUDENT_SCHEMAS_MAPPING)
    public List<Task> studentSchemas(@PathVariable String studentId){
        return studentSchemasService.studentSchemasOverview(studentId);
    }

    // TODO: 2/17/2021 what logging here?
    @GetMapping(MASTER_SCHEMAS_MAPPING)
    public List<Task> masterSchemasOverview(){
        return masterSchemasService.schemasRootTasks();
    }

    @GetMapping(MASTER_FILES_MAPPING)
    public List<String> schemasFileList() {
        return masterSchemasService.schemasFileList();
    }
}

