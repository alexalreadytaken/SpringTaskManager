package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.handlers.exceptions.forClient.ContentNotFoundException;
import com.bestSpringApplication.taskManager.handlers.exceptions.forClient.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.handlers.exceptions.forClient.ServerException;
import com.bestSpringApplication.taskManager.handlers.exceptions.internal.ParseException;
import com.bestSpringApplication.taskManager.handlers.parsers.SchemaParser;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractTask;
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

    @Value("${task.pool.path}")
    private String taskPoolPath;

    @NonNull private final SchemaParser schemaParser;

    private Map<String, AbstractStudySchema> masterSchemas;

    @PostConstruct
    private void init(){
        initFromXml();
    }

    // TODO: 3/3/2021 HOW DELETE F*****G FILE
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initFromXml() {
        log.trace("Started initialization");
        masterSchemas = new HashMap<>();
        File tasksDir = new File(taskPoolPath);
        if (tasksDir.exists()){
            Optional<File[]> files = Optional
                    .ofNullable(tasksDir.listFiles(el -> !el.isDirectory()));
            files.ifPresent(files0 -> Arrays.stream(files0).forEach(file -> {
                String fileName = file.getName();
                try {
                    log.trace("getting file '{}' to parse", fileName);
                    AbstractStudySchema schemaFromDir = schemaParser.parse(file);
                    log.trace("putting schema to Schemas,file:{}", fileName);
                    put(schemaFromDir);
                }  catch (ParseException e) {
                    log.error("error with parse:{}, file:{}",e.getMessage(), fileName);
                }
            }));
        }else {
            log.warn("directory '{}' not found, creating...", taskPoolPath);
            tasksDir.mkdir();
        }
    }

    // FIXME: 2/20/2021 return only valid files
    public List<String> schemasFileList() {
        log.trace("file list request");
        File file = new File(taskPoolPath);
        Optional<File[]> filesOpt = Optional
                .ofNullable(file.listFiles(file0->!file0.isDirectory()&&file0.canExecute()));
        List<String> fileNames = new ArrayList<>();
        filesOpt.ifPresent(files-> Arrays.stream(files).map(File::getName).forEach(fileNames::add));
        log.trace("return file list = {}",fileNames);
        return fileNames;
    }

    public List<AbstractTask> schemasRootTasks(){
        return masterSchemas
                .values()
                .stream()
                .map(AbstractStudySchema::getRootTask)
                .collect(Collectors.toList());
    }

    public AbstractStudySchema schemaByKey(String schemaKey) {
        return Optional.ofNullable(masterSchemas.get(schemaKey))
                .orElseThrow(()->{
                    log.error("schema with key = '{}' not found",schemaKey);
                    return new ContentNotFoundException("Курс не найден");
                });
    }

    // TODO: 2/17/2021 maybe transactional?
    public void putAndSaveFile(MultipartFile file)  {
        try {
            AbstractStudySchema studySchema = schemaParser.parse(file);
            put(studySchema);
            saveFile(file);
        }catch (ParseException ex){
            log.error("error with parse:{} file:{}",ex.getLocalizedMessage(),file.getOriginalFilename());
            throw new IllegalFileFormatException("загрузка файла не удалась,проверьте структуру своего файла");
        } catch (IOException e) {
            log.error("unknown io exception = {}",e.getMessage());
            throw new ServerException("Ошибка при загрузке файла,пожалуйста,повторите позже");
        }
    }

    public void put(AbstractStudySchema studySchema){
        String key = studySchema.getUniqueKey();
        masterSchemas.put(key,studySchema);
    }

    public void saveFile(MultipartFile file) throws IOException {
        File initialFile = new File(taskPoolPath + file.getOriginalFilename());
        log.trace("transfer file to '{}'",initialFile.getAbsolutePath());
        file.transferTo(initialFile);
    }

}
