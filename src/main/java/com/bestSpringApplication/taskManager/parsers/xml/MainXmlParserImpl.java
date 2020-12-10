package com.bestSpringApplication.taskManager.parsers.xml;

import com.bestSpringApplication.taskManager.models.xmlTask.implementations.MainXml;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskDependencyImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskImpl;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainXmlParserImpl {

    //fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme
    public static MainXml mainParser(InputStream is) throws JDOMException, IOException {
        Document mainDocument = new SAXBuilder().build(is);
        return parse(mainDocument);
    }
    public static MainXml mainParser(String str) throws JDOMException, IOException {
        Document mainDocument = new SAXBuilder().build(str);
        return parse(mainDocument);
    }
    //fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme


    private static MainXml parse(Document mainDocument){
        Element mainRootElement = mainDocument.getRootElement();
        MainXml.MainXmlBuilder mainXmlBuilder = MainXml.startBuildXml();
        
        Optional<Element> fieldList = Optional.ofNullable(mainRootElement.getChild("task-field-list"));
        Optional<Element> dependencyList = Optional.ofNullable(mainRootElement.getChild("task-dependency-list"));

        fieldList.ifPresent(fields -> {
            mainXmlBuilder.setTaskFieldList(TaskParserImpl.fieldToMap(fields, "field","no", "name"));

        });
        dependencyList.ifPresent(list ->{
            List<Element> DependencyElements = list.getChildren("task-dependency");
            List<TaskDependencyImpl> relativesList = DependencyElements.stream().map(DependencyChild -> new TaskDependencyImpl(
                                    DependencyChild.getChildText("task-predecessor-id"),
                                    DependencyChild.getChildText("task-successor-id"))).collect(Collectors.toList());
            mainXmlBuilder.setTaskDependencyList(relativesList);

            //fixme
            TaskImpl rootTask = TaskParserImpl.parse(mainRootElement.getChild("task"), relativesList);
            mainXmlBuilder.setTask(rootTask);

            TaskParserImpl.depthFirst(rootTask);
        });



        return mainXmlBuilder.build();
    }

}
