package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.utils.VersionedList;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.ContentNotFoundException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.ServerException;
import com.bestSpringApplication.taskManager.utils.exceptions.internal.ParseException;
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
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MasterSchemasService {

    @Value("${xml.task.pool.path}")
    private String xmlTaskPoolPath;

    @NonNull private final SchemaParser schemaParser;

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
        if (tasksDir.exists()){
            Optional<File[]> files = Optional
                    .ofNullable(tasksDir.listFiles(el -> !el.isDirectory()));
            files.ifPresent(files0 -> Arrays.stream(files0).forEach(file -> {
                String fileName = file.getName();
                try {
                    log.trace("getting file '{}' to parse", fileName);
                    AbstractStudySchema schemaFromDir = schemaParser.parse(file);
                    log.trace("putting schema to map,file:{}", fileName);
                    put(schemaFromDir);
                }  catch (ParseException e) {
                    log.error("error with parse:{}",e.getMessage());
                    log.error("deleting file '{}' ",fileName);
                    //fixme later
//                    FileDeleter.deleteAsync(file,2000);
                }
            }));
        }else {
            log.warn("directory '{}' not found,try to create...", xmlTaskPoolPath);
            tasksDir.mkdir();
        }
    }

    public List<String> schemasFileList() {
        log.trace("file list request");
        File file = new File(xmlTaskPoolPath);
        Optional<File[]> filesOpt = Optional
                .ofNullable(file.listFiles(file0->!file0.isDirectory()));
        List<String> fileNames = new ArrayList<>();
        filesOpt
                .ifPresent(files-> Arrays.stream(files)
                        .map(File::getName)
                        .forEach(fileNames::add));
        log.trace("return file list = {}",fileNames);
        return fileNames;
    }

    public List<AbstractTask> schemasRootTasks(){
        return masterSchemas
                .values()
                .stream()
                .map(VersionedList::getNewest)
                .map(AbstractStudySchema::getRootTask)
                .collect(Collectors.toList());
    }

    public AbstractStudySchema schemaByKey(String schemaKey) {
        return Optional.ofNullable(masterSchemas.get(schemaKey))
                .map(VersionedList::getNewest)
                .orElseThrow(()->{
                    log.warn("schema with key = '{}' not found",schemaKey);
                    return new ContentNotFoundException("Курс не найден");
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
            throw new IllegalFileFormatException("загрузка файла не удалась,проверьте структуру своего файла");
        } catch (IOException e) {
            masterSchemas.remove(studySchema.getKey());

            log.error("unknown io exception = {}, removing schema from map",e.getMessage());
            throw new ServerException("Ошибка при загрузке файла,пожалуйста,повторите позже");
        }
    }

    public void put(AbstractStudySchema studySchema){
        String key = studySchema.getKey();
        VersionedList<AbstractStudySchema> schemaList = Optional
                .ofNullable(masterSchemas.get(key))
                .orElseGet(VersionedList::new);
        schemaList.put(studySchema);
        masterSchemas.put(key,schemaList);
    }

    public void saveFile(MultipartFile file) throws IOException {
        File initialFile = new File(xmlTaskPoolPath + file.getOriginalFilename());
        log.trace("transfer file to '{}'",initialFile.getAbsolutePath());
        file.transferTo(initialFile);
    }

}
