package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.classes.StudySchema;
import com.bestSpringApplication.taskManager.models.classes.StudyTask;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SchemasService {
    List<String> schemasFilenamesList();

    List<StudyTask> getSchemasRootTasks();

    StudySchema getSchemaById(String schemaId);

    StudyTask getTaskByIdInSchema(String taskId, String schemaId);

    boolean schemaExists(String schemaId);

    boolean taskInSchemaExists(String schemaId, String taskId);

    void validateSchemaExistsOrThrow(String schemaId);

    void validateTaskInSchemaExistsOrThrow(String schemaId,String taskId);

    void putAndSaveFileWithValidation(MultipartFile file);

    void put(StudySchema studySchema);

    void saveFile(MultipartFile file) throws IOException;
}
