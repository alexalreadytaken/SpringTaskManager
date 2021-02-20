package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.handlers.StudyParseHandler;
import com.bestSpringApplication.taskManager.handlers.exceptions.internal.ParseException;
import com.bestSpringApplication.taskManager.handlers.exceptions.internal.SchemaParseException;
import com.bestSpringApplication.taskManager.handlers.parsers.SchemaParser;
import com.bestSpringApplication.taskManager.handlers.parsers.TaskParser;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.study.classes.DefaultStudySchemaImpl;
import com.bestSpringApplication.taskManager.models.study.classes.DependencyImpl;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class XmlSchemaParser implements SchemaParser {

    @NonNull private final TaskParser taskParser;

    @Override
    public StudySchema parse(Object parsable) throws ParseException {
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            if (parsable instanceof File){
                File file = (File) parsable;
                Document fileDocument = saxBuilder.build(file);
                return parseSchemaXml(fileDocument);
            }else if (parsable instanceof MultipartFile){
                MultipartFile multipartFile = (MultipartFile) parsable;
                Document multipartFileDocument = saxBuilder.build(multipartFile.getInputStream());
                return parseSchemaXml(multipartFileDocument);
            }else {
                throw new SchemaParseException("Parsable object is not file or multipart file");
            }
        } catch (IOException | JDOMException e) {
            throw new ParseException(String.format("Some error with getting file = %s",e.getMessage()));
        }
    }

    private StudySchema parseSchemaXml(Document mainDocument){
        Element rootElement = mainDocument.getRootElement();
        // FIXME: 2/17/2021 somehow use interface
        AbstractStudySchema studySchema = new DefaultStudySchemaImpl();

        log.trace("Starting parse root element:\n{}",rootElement.getContent());

        Element fieldListElem = Optional.ofNullable(rootElement.getChild("task-field-list"))
                .orElseThrow(()-> new SchemaParseException("fieldListElem is empty!"));
        Element dependencyListElem = Optional.ofNullable(rootElement.getChild("task-dependency-list"))
                .orElseThrow(()-> new SchemaParseException("dependencyListElement is empty!"));
        Element taskElem = Optional.ofNullable(rootElement.getChild("task"))
                .orElseThrow(()-> new SchemaParseException("taskElement is empty!"));

        Map<String, String> fieldsMap = StudyParseHandler.fieldToMap(fieldListElem, "field", "no", "name");
        List<Dependency> taskDependenciesList = parseDependenciesList(dependencyListElem);
        List<Task> tasksList = taskParser.parse(taskElem);
        StudyParseHandler.addTaskFields(tasksList,fieldsMap);
        Map<String, Task> completedTasksMap = new HashMap<>();

        tasksList.forEach(task -> completedTasksMap.put(task.getId(),task));

        studySchema.setRootTask(tasksList.get(0));
        studySchema.setDependencies(taskDependenciesList);
        studySchema.setTasksMap(completedTasksMap);

        log.trace("Returning study schema = {}",studySchema);

        return studySchema;
    }

    private List<Dependency> parseDependenciesList(Element dependencyListElem) {
        log.trace("Starting parse dependencies list xml element = {}",dependencyListElem);
        List<Element> DependencyElements = dependencyListElem.getChildren("task-dependency");
        return DependencyElements.stream().map(DependencyChild ->{
            String parent = DependencyChild.getChildText("task-predecessor-id");
            String child = DependencyChild.getChildText("task-successor-id");
            return new DependencyImpl(parent,child);
        }).collect(Collectors.toList());
    }
}
