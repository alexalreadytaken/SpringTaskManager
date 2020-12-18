package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.Controllers.TasksController;
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

        dependencyListElem.orElseThrow(()-> new JDOMException("Schema dependencyList is empty"));
        taskElem.orElseThrow(()-> new JDOMException("Schema taskElem is empty"));

        LOGGER.debug("Nice! dependencyList exist: {}, taskElem exist: {}",
                dependencyListElem.isPresent(),
                taskElem.isPresent());
        List<TaskDependency> taskDependencies = new ArrayList<>();
        Map<String,String> schemeFields = new HashMap<>();

        fieldListElem.ifPresent(fields ->
            schemeFields.putAll(TaskParserImpl.fieldToMap(fields, "field","no", "name"))
        );
        dependencyListElem.ifPresent(dependencyListElemOpt ->
            taskDependencies.addAll(parseDependenciesList(dependencyListElemOpt))
        );
        taskElem.ifPresent(tasksOpt-> {
            List<Task> tasks = TaskParserImpl.parse(tasksOpt, taskDependencies);
            if (schemeFields.size()!=0)addTaskFields(tasks,schemeFields);
            Map<String,Task> completeTasksList = new HashMap<>();
            tasks.forEach(task -> completeTasksList.put(((TaskImpl)task).getId(),task));
            tasksSchema.setTasksMap(completeTasksList);
        });

        tasksSchema.setTaskDependencies(taskDependencies);
        tasksSchema.setTasksGraph(
            makeTasksGraph(
                tasksSchema.getTasksMap(),
                tasksSchema.getTaskDependencies()
            ));
        LOGGER.debug("Dependencies: {}, TasksGraph: {}", !taskDependencies.isEmpty(), !tasksSchema.getTasksGraph().isEmpty());
        return tasksSchema;
    }

    private static Map<Task,List<Task>> makeTasksGraph(Map<String,Task> taskMap,List<TaskDependency> taskDependencies) throws JDOMException {
        Map<Task,List<Task>> tasksGraph = new HashMap<>();
        TaskDependency rootTask = taskDependencies.stream().filter(el -> ((TaskDependencyImpl) el).getTaskParentId().equals("root"))
            .findFirst().orElseThrow(() -> new JDOMException("Root task element not found!"));
        Stack<Task> taskStack = new Stack<>();
        taskStack.push(taskMap.get(((TaskDependencyImpl) rootTask).getTaskChildId()));
        while (!taskStack.empty()){
            TaskImpl task = ((TaskImpl) taskStack.pop());
            String taskId = task.getId();

            List<String> childesId =
                taskDependencies.stream()
                    .filter(el->((TaskDependencyImpl)el).getTaskParentId().equals(taskId))
                    .map(el->((TaskDependencyImpl)el).getTaskChildId())
                    .collect(Collectors.toList());
            List<Task> childTasks =
                childesId.stream()
                    .map(taskMap::get)
                    .collect(Collectors.toList());
            tasksGraph.put(task,childTasks);
            childTasks.forEach(taskStack::push);
        }
        return tasksGraph;
    }

    private static void addTaskFields(List<Task> tasks, Map<String, String> schemeFields) {
        tasks.forEach(task -> {
            TaskImpl taskImpl = (TaskImpl) task;
            taskImpl.getFields().ifPresent(taskFields->{
                Map<String,String> completedTaskFields = new HashMap<>();
                for (int i = 0; i < taskFields.size(); i++) {
                    String i0 = String.valueOf(i);
                    String key = schemeFields.get(i0);
                    String value = taskFields.get(i0);
                    completedTaskFields.put(key,value);
                }
                taskImpl.setFields(completedTaskFields);
            });
        });
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
