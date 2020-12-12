package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.models.xmlTask.implementations.MainXml;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskDependencyImpl;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainXmlParserImpl {

    public static MainXml parseCourseXml(Document mainDocument){
        Element mainRootElement = mainDocument.getRootElement();
        MainXml.MainXmlBuilder mainXmlBuilder = MainXml.startBuildXml();

        Optional<Element> fieldListElem = Optional.ofNullable(mainRootElement.getChild("task-field-list"));
        Optional<Element> dependencyListElem = Optional.ofNullable(mainRootElement.getChild("task-dependency-list"));
        Optional<Element> taskElem = Optional.ofNullable(mainRootElement.getChild("task"));

        List<TaskDependencyImpl> dependencies = new ArrayList<>();

        fieldListElem.ifPresent(fields ->
            mainXmlBuilder.setTaskFieldList(TaskParserImpl.fieldToMap(fields, "field","no", "name"))
        );

        dependencyListElem.ifPresent(dependencyList ->{
            List<Element> DependencyElements = dependencyList.getChildren("task-dependency");
            dependencies.addAll(DependencyElements.stream().map(DependencyChild ->{
                String parent = DependencyChild.getChildText("task-predecessor-id");
                String child = DependencyChild.getChildText("task-successor-id");
                return new TaskDependencyImpl(parent,child);
            }).collect(Collectors.toList()));
        });
        taskElem.ifPresent(tasks->
            mainXmlBuilder.setTasks(TaskParserImpl.parse(tasks,dependencies))
        );
        mainXmlBuilder.setTaskDependencyList(dependencies);

        return mainXmlBuilder.build();
    }

}
