package com.bestSpringApplication.taskManager.utils.parsers.xml;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.DefaultStudySchemaImpl;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.classes.TaskImpl;
import com.bestSpringApplication.taskManager.models.enums.RelationType;
import com.bestSpringApplication.taskManager.utils.StudyParseHandler;
import com.bestSpringApplication.taskManager.utils.exceptions.internal.ParseException;
import com.bestSpringApplication.taskManager.utils.exceptions.internal.SchemaParseException;
import com.bestSpringApplication.taskManager.utils.parsers.SchemaParser;
import com.bestSpringApplication.taskManager.utils.parsers.TaskParser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@RequiredArgsConstructor
public class XmlSchemaParser implements SchemaParser {

    @NonNull private final ApplicationContext applicationContext;

    @Override
    public AbstractStudySchema parse(Object parsable) throws ParseException {
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

    private AbstractStudySchema parseSchemaXml(Document mainDocument){
        Element rootElement = mainDocument.getRootElement();
        log.trace("Starting parse root element:\n{}",rootElement.getContent());
        Element fieldListElem = Optional.ofNullable(rootElement.getChild("task-field-list"))
                .orElseThrow(()-> new SchemaParseException("fieldListElem is empty!"));
        Element dependencyListElem = Optional.ofNullable(rootElement.getChild("task-dependency-list"))
                .orElseThrow(()-> new SchemaParseException("dependencyListElement is empty!"));
        Element taskElem = Optional.ofNullable(rootElement.getChild("task"))
                .orElseThrow(()-> new SchemaParseException("taskElement is empty!"));
        Map<String, String> fieldsMap = StudyParseHandler.xmlFieldToMap(fieldListElem, "field", "no", "name");
        List<DependencyWithRelationType> dependenciesList = parseDependenciesList(dependencyListElem);
        TaskParser taskParser = applicationContext.getBean(XmlTaskParser.class, taskElem);
        Map<String, AbstractTask> tasksMap = taskParser.getTasks();
        dependenciesList.addAll(taskParser.getHierarchicalDependencies());
        addFieldsToTasks(tasksMap,fieldsMap);
        log.trace("Returning study schema = {}",tasksMap.get("root"));
        return new DefaultStudySchemaImpl(tasksMap,dependenciesList,tasksMap.remove("root"));
    }

    private List<DependencyWithRelationType> parseDependenciesList(Element dependencyListElem) {
        log.trace("Starting parse dependencies list xml element = {}",dependencyListElem);
        List<Element> DependencyElements = dependencyListElem.getChildren("task-dependency");
        return DependencyElements.stream()
                .map(DependencyChild ->{
                    String parent = DependencyChild.getChildText("task-predecessor-id");
                    String child = DependencyChild.getChildText("task-successor-id");
                    return new DependencyWithRelationType(RelationType.WEAK,parent,child);
                }).collect(toList());
    }

    private void addFieldsToTasks(Map<String, AbstractTask> tasks, Map<String, String> schemaFields) {
        tasks.values().stream()
                .filter(TaskImpl.class::isInstance)
                .map(TaskImpl.class::cast)
                .forEach(task -> {
                    Map<String, String> taskFields = task.getFields();
                    if (taskFields!=null){
                        for (int i = 0;i <  taskFields.size(); i++) {
                            String i0 = String.valueOf(i);
                            String key = schemaFields.get(i0);
                            String value = taskFields.remove(i0);
                            taskFields.put(key,value);
                        }
                    }
                });
    }
}
