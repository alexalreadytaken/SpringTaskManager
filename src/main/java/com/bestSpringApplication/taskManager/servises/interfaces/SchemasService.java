package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SchemasService {
    List<String> schemasFilenamesList();

    List<AbstractTask> getSchemasRootTasks();

    AbstractStudySchema getSchemaById(String schemaId);

    AbstractTask getTaskByIdInSchema(String taskId, String schemaId);

    boolean schemaExists(String schemaId);

    boolean taskInSchemaExists(String schemaId, String taskId);

    void validateSchemaExistsOrThrow(String schemaId);

    void validateTaskInSchemaExistsOrThrow(String schemaId,String taskId);

    void putAndSaveFileWithValidation(MultipartFile file);

    void put(AbstractStudySchema studySchema);

    void saveFile(MultipartFile file) throws IOException;
}
