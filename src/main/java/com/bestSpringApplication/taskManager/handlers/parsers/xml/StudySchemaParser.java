package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.handlers.TasksHandler;
import com.bestSpringApplication.taskManager.models.study.implementations.DependencyImpl;
import com.bestSpringApplication.taskManager.models.study.implementations.StudySchemaImpl;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class StudySchemaParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudySchemaParser.class);

    public static StudySchema parseSchemaXml(Document mainDocument) throws JDOMException {
        Element rootElement = mainDocument.getRootElement();
        StudySchemaImpl studySchema = new StudySchemaImpl();

        LOGGER.trace("Start parse root element:\n{}",rootElement.getContent());

        Element fieldListElem = Optional.ofNullable(rootElement.getChild("task-field-list"))
                .orElseThrow(()-> new JDOMException("fieldListElem is empty!"));;
        Element dependencyListElem = Optional.ofNullable(rootElement.getChild("task-dependency-list"))
                .orElseThrow(()-> new JDOMException("dependencyListElement is empty!"));
        Element taskElem = Optional.ofNullable(rootElement.getChild("task"))
                .orElseThrow(()-> new JDOMException("taskElement is empty!"));

        Map<String, String> fieldsMap = TaskParser.fieldToMap(fieldListElem, "field", "no", "name");
        List<Dependency> taskDependenciesList = parseDependenciesList(dependencyListElem);
        List<Task> tasksList = TaskParser.parseFromXml(taskElem);
        TasksHandler.addTaskFields(tasksList,fieldsMap);
        Map<String, Task> completeTasksMap = new HashMap<>();

        completeTasksMap.put("root",tasksList.remove(0)); // experimental

        tasksList.forEach(task -> completeTasksMap.put(task.getId(),task));

        studySchema.setDependencies(taskDependenciesList);
        studySchema.setTasksMap(completeTasksMap);

        return studySchema;
    }

    private static List<Dependency> parseDependenciesList(Element dependencyListElem) {
        List<Element> DependencyElements = dependencyListElem.getChildren("task-dependency");
        return DependencyElements.stream().map(DependencyChild ->{
            String parent = DependencyChild.getChildText("task-predecessor-id");
            String child = DependencyChild.getChildText("task-successor-id");
            return new DependencyImpl(parent,child);
        }).collect(Collectors.toList());
    }

}
