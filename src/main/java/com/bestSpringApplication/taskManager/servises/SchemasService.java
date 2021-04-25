package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.servises.interfaces.SchemasProvider;
import com.bestSpringApplication.taskManager.utils.VersionedList;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.ContentNotFoundException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.ServerException;
import com.bestSpringApplication.taskManager.utils.exceptions.internal.ParseException;
import com.bestSpringApplication.taskManager.utils.exceptions.internal.PostConstructInitializationException;
import com.bestSpringApplication.taskManager.utils.parsers.SchemaParser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchemasService implements SchemasProvider {

    @Value("${xml.task.pool.path}")
    private String xmlTaskPoolPath;
    @Value("${invalid.files.pool.path}")
    private String invalidFilesPoolPath;

    // TODO: 4/15/21 list of parsers and dirs
    @NonNull private final SchemaParser schemaParser;

    // TODO: 4/6/21 really version control
    private Map<String, VersionedList<AbstractStudySchema>> masterSchemas;

    @PostConstruct
    private void init(){
        masterSchemas = new HashMap<>();
        initFromXml();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initFromXml() {
        log.trace("Started initialization");
        File tasksDir = new File(xmlTaskPoolPath);
        File invalidFilesDir = new File(invalidFilesPoolPath);
        if(!tasksDir.exists()||!invalidFilesDir.exists()){
            log.error("invalid directories state = tasksDir exists:{},invalidFilesDir exists:{}",
                    tasksDir.exists(),invalidFilesDir.exists());
            throw new PostConstructInitializationException("One of the directories was not found");
        }
        Arrays.stream(tasksDir.listFiles(File::isFile))
                .forEach(file -> {
                    String fileName = file.getName();
                    try {
                        log.trace("getting file '{}' to parse", fileName);
                        AbstractStudySchema schemaFromDir = schemaParser.parse(file);
                        log.trace("putting schema to map,file:{}", fileName);
                        put(schemaFromDir);
                    }  catch (ParseException e) {
                        log.error("error with parse:{}",e.getMessage());
                        log.error("moving file to invalid directory'{}' ",fileName);
                        file.renameTo(new File(invalidFilesPoolPath+fileName));
                    }
                });

    }

    public List<String> schemasFilenamesList() {
        File dir = new File(xmlTaskPoolPath);
        return Arrays.stream(dir.listFiles(File::isFile))
                .map(File::getName)
                .collect(toList());
    }

    public List<AbstractTask> getSchemasRootTasks(){
        return masterSchemas
                .values().stream()
                .map(VersionedList::getNewest)
                .map(AbstractStudySchema::getRootTask)
                .collect(toList());
    }

    public AbstractStudySchema getSchemaById(String schemaId) {
        return Optional.ofNullable(masterSchemas.get(schemaId))
                .map(VersionedList::getNewest)
                .orElseThrow(()->{
                    log.warn("schema with id = '{}' not found",schemaId);
                    return new ContentNotFoundException("Курс не найден");
                });
    }

    public AbstractTask getTaskByIdInSchema(String taskId, String schemaId){
        AbstractStudySchema schema = getSchemaById(schemaId);
        return Optional.ofNullable(schema.getTasksMap())
                .map(taskMap->taskMap.get(taskId))
                .orElseThrow(() -> {
                    log.warn("can`t get task by id '{}' in schema '{}'", taskId, schemaId);
                    return new ContentNotFoundException("Задание не найдено");
                });
    }

    public void putAndSaveFile(MultipartFile file)  {
        //костыльно
        AbstractStudySchema studySchema = null;
        try {
            studySchema = schemaParser.parse(file);
            put(studySchema);
            saveFile(file);
        }catch (ParseException ex){
            log.error("error with parse:{} file:{}",ex.getLocalizedMessage(),file.getOriginalFilename());
            throw new IllegalFileFormatException("загрузка файла не удалась, проверьте структуру своего файла");
        } catch (IOException ex) {
            masterSchemas.computeIfPresent(studySchema.getId(),(id,list)->{
                AbstractStudySchema removed = list.removeNewets();
                log.error("unknown io exception = {}, removing schema '{}'",ex.getMessage(),removed);
                return list;
            });
            throw new ServerException("Ошибка при загрузке файла, пожалуйста, повторите позже");
        }
    }

    public void put(AbstractStudySchema studySchema){
        String id = studySchema.getId();
        if (masterSchemas.containsKey(id)) {
            masterSchemas.get(id)
                    .put(studySchema);
        } else {
            masterSchemas.put(id,VersionedList.of(studySchema));
        }
    }

    public void saveFile(MultipartFile file) throws IOException {
        File initialFile = new File(xmlTaskPoolPath + file.getOriginalFilename());
        log.trace("transfer file to '{}'",initialFile.getAbsolutePath());
        file.transferTo(initialFile);
    }

}
