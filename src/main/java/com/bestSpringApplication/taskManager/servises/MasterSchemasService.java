package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.handlers.exceptions.ContentNotFoundException;
import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalXmlFormatException;
import com.bestSpringApplication.taskManager.handlers.exceptions.ServerException;
import com.bestSpringApplication.taskManager.handlers.parsers.xml.StudySchemaParser;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MasterSchemasService {

    @Value("${task.pool.path}")
    private String taskPoolPath;

    @NonNull private final StudySchemaParser studySchemaParser;

    private Map<String, StudySchema> masterSchemas;

    @PostConstruct
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void init(){
        log.trace("Started initialization");
        masterSchemas = new HashMap<>(1);
        File tasksDir = new File(taskPoolPath);
        if (tasksDir.exists()){
            Optional<File[]> files = Optional
                    .ofNullable(tasksDir.listFiles(el -> !el.isDirectory()));
            files.ifPresent(files0 -> {
                    masterSchemas = new HashMap<>(files0.length*2);
                    Arrays.stream(files0).forEach(file -> {
                        String fileName = file.getName();
                        try {
                            log.trace("getting file '{}' to parse", fileName);
                            InputStream fileInputStream = new FileInputStream(file);
                            Document schemaDoc = new SAXBuilder().build(fileInputStream);
                            StudySchema schemaFromDir = studySchemaParser.parseSchemaXml(schemaDoc);
                            log.trace("putting schema to Schemas,file:{}", fileName);
                            put(schemaFromDir);
                        } catch (FileNotFoundException e) {
                            log.warn("file {} was deleted in initializing time",fileName);
                        } catch (JDOMException e) {
                            log.error("error with parse XML:{},file:{}",e.getMessage(), fileName);
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                    });
        });
        }else {
            log.warn("directory '{}' not found, creating...",taskPoolPath);
            tasksDir.mkdir();
        }
    }

    public List<String> schemasFileList() {
        log.trace("file list request");
        File file = new File(taskPoolPath);
        Optional<File[]> filesOpt = Optional.ofNullable(file.listFiles(File::isFile));
        List<String> fileNames = new ArrayList<>();
        filesOpt.ifPresent(files-> Arrays.stream(files).map(File::getName).forEach(fileNames::add));
        log.trace("return file list = {}",fileNames);
        return fileNames;
    }

    public List<Task> schemasRootTasks(){
        return masterSchemas
                .values()
                .stream()
                .map(StudySchema::getRootTask)
                .collect(Collectors.toList());
    }

    public StudySchema schemaByKey(String schemaKey) {
        return Optional.ofNullable(masterSchemas.get(schemaKey))
                .orElseThrow(()->{
                    log.error("schema with key = '{}' not found",schemaKey);
                    return new ContentNotFoundException("Курс не найден");
                });
    }

    // TODO: 2/17/2021 maybe transactional?
    public void putAndSaveFile(MultipartFile file)  {
        try {
            Document fileDoc = new SAXBuilder().build(file.getInputStream());
            StudySchema studySchema = studySchemaParser.parseSchemaXml(fileDoc);
            put(studySchema);
            saveFile(file);
        }catch (JDOMException ex){
            log.error("error with XML parse:{} file:{}",ex.getLocalizedMessage(),file.getOriginalFilename());
            throw new IllegalXmlFormatException("загрузка файла не удалась,проверьте структуру своего XML файла");
        } catch (IOException e) {
            log.error("unknown io exception = {}",e.getMessage());
            throw new ServerException("Ошибка при загрузке файла,пожалуйста,повторите позже");
        }
    }

    public void put(StudySchema studySchema){
        String key = studySchema.getRootTask().getName();
        masterSchemas.put(key,studySchema);
    }

    public void saveFile(MultipartFile file) throws IOException {
        File initialFile = new File(taskPoolPath + file.getOriginalFilename());
        log.trace("transfer file to '{}'",initialFile.getAbsolutePath());
        file.transferTo(initialFile);
    }

}
