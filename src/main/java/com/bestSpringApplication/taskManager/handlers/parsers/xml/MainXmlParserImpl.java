package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.models.xmlTask.implementations.MainXml;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskDependencyImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.*;
import java.util.stream.Collectors;

public class MainXmlParserImpl {

    public static MainXml parseCourseXml(Document mainDocument){
        Element mainRootElement = mainDocument.getRootElement();
        MainXml mainXml = new MainXml();

        Optional<Element> fieldListElem = Optional.ofNullable(mainRootElement.getChild("task-field-list"));
        Optional<Element> dependencyListElem = Optional.ofNullable(mainRootElement.getChild("task-dependency-list"));
        Optional<Element> taskElem = Optional.ofNullable(mainRootElement.getChild("task"));

        List<TaskDependencyImpl> dependencies = new ArrayList<>();
        Map<String,String> tasksFields = new HashMap<>();

        fieldListElem.ifPresent(fields ->
            tasksFields.putAll(TaskParserImpl.fieldToMap(fields, "field","no", "name"))
        );

        dependencyListElem.ifPresent(dependencyList ->{
            List<Element> DependencyElements = dependencyList.getChildren("task-dependency");
            dependencies.addAll(DependencyElements.stream().map(DependencyChild ->{
                String parent = DependencyChild.getChildText("task-predecessor-id");
                String child = DependencyChild.getChildText("task-successor-id");
                return new TaskDependencyImpl(parent,child);
            }).collect(Collectors.toList()));
        });
        taskElem.ifPresent(tasksOpt-> {
            List<Task> tasks = TaskParserImpl.parse(tasksOpt, dependencies);
            if (tasksFields.size()!=0){
                tasks.forEach(task -> {
                    TaskImpl taskImpl = (TaskImpl) task;
                    taskImpl.getFields().ifPresent(taskFields->{
                        Map<String,String> completedTaskFields = new HashMap<>();
                        for (int i = 0; i < taskFields.size(); i++) {
                            String i0 = String.valueOf(i);
                            String key = tasksFields.get(i0);
                            String value = taskFields.get(i0);
                            completedTaskFields.put(key,value);
                        }
                        taskImpl.setFields(completedTaskFields);
                    });
                });
            }
            mainXml.setTasks(tasks);
        });
        mainXml.setTaskDependencyList(dependencies);

        return mainXml;
    }

}
