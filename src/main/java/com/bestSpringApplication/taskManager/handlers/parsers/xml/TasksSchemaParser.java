package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.handlers.TasksHandler;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskDependencyImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TasksSchema;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.TaskDependency;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class TasksSchemaParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksSchemaParser.class);

    public static TasksSchema parseSchemaXml(Document mainDocument) throws JDOMException {

        Element mainRootElement = mainDocument.getRootElement();
        TasksSchema tasksSchema = new TasksSchema();

        Optional<Element> fieldListElem = Optional.ofNullable(mainRootElement.getChild("task-field-list"));
        Optional<Element> dependencyListElem = Optional.ofNullable(mainRootElement.getChild("task-dependency-list"));
        Optional<Element> taskElem = Optional.ofNullable(mainRootElement.getChild("task"));

        LOGGER.trace("Start parse: dependencyList Xml element exist: {}, task Xml element exist: {}",
            dependencyListElem.isPresent(),
            taskElem.isPresent());

        dependencyListElem.orElseThrow(()-> new JDOMException("Schema dependencyList is empty!"));
        taskElem.orElseThrow(()-> new JDOMException("Schema taskElem is empty!"));

        List<TaskDependency> taskDependencies = new ArrayList<>();
        Map<String,String> schemeFields = new HashMap<>();

        fieldListElem.ifPresent(fields ->
            schemeFields.putAll(TaskParserImpl.fieldToMap(fields, "field","no", "name"))
        );
        dependencyListElem.ifPresent(dependencyListElemOpt ->
            taskDependencies.addAll(parseDependenciesList(dependencyListElemOpt))
        );

        List<Task> tasks = TaskParserImpl.parse(taskElem.get(), taskDependencies);
        if (schemeFields.size()!=0)TasksHandler.addTaskFields(tasks,schemeFields);
        Map<String,Task> completeTasksList = new HashMap<>();
        tasks.forEach(task -> completeTasksList.put(task.getId(),task));
        tasksSchema.setTasksMap(completeTasksList);


        tasksSchema.setTaskDependencies(taskDependencies);
        tasksSchema.setTasksGraph(
            TasksHandler.makeTasksGraph(
                tasksSchema.getTasksMap(),
                tasksSchema.getTaskDependencies()
            ));

        return tasksSchema;
    }

    private static List<TaskDependency> parseDependenciesList(Element dependencyListElem) {
        List<Element> DependencyElements = dependencyListElem.getChildren("task-dependency");
        return DependencyElements.stream().map(DependencyChild ->{
            String parent = DependencyChild.getChildText("task-predecessor-id");
            String child = DependencyChild.getChildText("task-successor-id");
            return new TaskDependencyImpl(parent,child);
        }).collect(Collectors.toList());
    }

}
