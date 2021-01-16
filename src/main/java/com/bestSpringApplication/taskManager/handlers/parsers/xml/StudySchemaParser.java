package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.handlers.TasksHandler;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.DependencyImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.StudySchemeImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class StudySchemaParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudySchemaParser.class);

    public static StudySchemeImpl parseSchemaXml(Document mainDocument) throws JDOMException {

        Element mainRootElement = mainDocument.getRootElement();
        StudySchemeImpl studySchemeImpl = new StudySchemeImpl();

        Optional<Element> fieldListElem = Optional.ofNullable(mainRootElement.getChild("task-field-list"));
        Optional<Element> dependencyListElem = Optional.ofNullable(mainRootElement.getChild("task-dependency-list"));
        Optional<Element> taskElem = Optional.ofNullable(mainRootElement.getChild("task"));

        LOGGER.trace("Start parse: dependencyList exist={}, task exist={}",
            dependencyListElem.isPresent(),
            taskElem.isPresent());

        dependencyListElem.orElseThrow(()-> new JDOMException("Schema dependencyList is empty!"));
        taskElem.orElseThrow(()-> new JDOMException("Schema taskElem is empty!"));

        List<Dependency> taskDependencies = new ArrayList<>();
        Map<String,String> schemeFields = new HashMap<>();

        fieldListElem.ifPresent(fields ->
            schemeFields.putAll(TaskParser.fieldToMap(fields, "field","no", "name"))
        );
        dependencyListElem.ifPresent(dependencyListElemOpt ->
            taskDependencies.addAll(parseDependenciesList(dependencyListElemOpt))
        );

        List<Task> tasks = TaskParser.parseFromXml(taskElem.get(), taskDependencies);
        if (schemeFields.size()!=0)TasksHandler.addTaskFields(tasks,schemeFields);
        Map<String,Task> completeTasksMap = new HashMap<>();
        tasks.forEach(task -> completeTasksMap.put(task.getId(),task));

        Map<Task, List<Task>> tasksGraph = TasksHandler
            .makeTasksGraph(completeTasksMap,taskDependencies);

        studySchemeImpl.setTasksMap(completeTasksMap);
        studySchemeImpl.setTaskDependencies(taskDependencies);
        studySchemeImpl.setTasksGraph(tasksGraph);

        return studySchemeImpl;
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
