package com.bestSpringApplication.taskManager.Controllers;

import com.bestSpringApplication.taskManager.models.classes.StudySchema;
import com.bestSpringApplication.taskManager.models.classes.StudyTask;
import com.bestSpringApplication.taskManager.servises.interfaces.SchemasService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class SchemasController {

    private final String SCHEMAS_ROOT_TASKS =                   "/schemas";
    private final String SCHEMAS_FILES =                        "/schemas/files";
    private final String SCHEMA_BY_ID =                         "/schema/{schemaId}";

    @NonNull private final SchemasService schemasService;

    @PostMapping(SCHEMAS_FILES)
    @ResponseStatus(HttpStatus.OK)
    public void newSchema(@RequestParam("file") MultipartFile file){
        log.trace("Receive file:'{}' trying parse", file.getOriginalFilename());
        schemasService.putAndSaveFileWithValidation(file);
    }

    @GetMapping(SCHEMA_BY_ID)
    public StudySchema masterSchemaById(@PathVariable String schemaId){
        log.trace("request for schema '{}' information",schemaId);
        schemasService.validateSchemaExistsOrThrow(schemaId);
        return schemasService.getSchemaById(schemaId);
    }

    @GetMapping(SCHEMAS_ROOT_TASKS)
    public List<StudyTask> masterSchemasOverview(){
        return schemasService.getSchemasRootTasks();
    }

    @GetMapping(SCHEMAS_FILES)
    public List<String> schemasFileList() {
        return schemasService.schemasFilenamesList();
    }
}
