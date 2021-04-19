package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SchemasProviderService {
    List<String> schemasFilenamesList();

    List<AbstractTask> getSchemasRootTasks();

    AbstractStudySchema getSchemaById(String schemaId);

    AbstractTask getTaskByIdInSchema(String taskId, String schemaId);

    void putAndSaveFile(MultipartFile file);

    void put(AbstractStudySchema studySchema);

    void saveFile(MultipartFile file) throws IOException;
}
