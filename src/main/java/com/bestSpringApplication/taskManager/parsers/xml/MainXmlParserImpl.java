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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainXmlParserImpl {

    //fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme
    public static MainXml mainParser(InputStream is) throws JDOMException, IOException {
        Document mainDocument = new SAXBuilder().build(is);
        return parse(mainDocument);
    }
    public static MainXml mainParser(String is) throws JDOMException, IOException {
        Document mainDocument = new SAXBuilder().build(is);
        return parse(mainDocument);
    }
    //fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme fixme


    private static MainXml parse(Document mainDocument){
        MainXml.MainXmlBuilder mainXmlBuilder = MainXml.startBuildXml();
        Element element = mainDocument.getRootElement();
        TaskImpl parse = TaskParserImpl.parse(element.getChild("task"), TaskImpl.startBuildTask().setTaskName("root").build());
        Optional<Element> taskFieldListElem = Optional.ofNullable(element.getChild("task-field-list"));
        Optional<Element> taskDependency = Optional.ofNullable(element.getChild("task-dependency-list"));
        mainXmlBuilder.setTask(parse);
        taskFieldListElem.ifPresent(fields -> {
            mainXmlBuilder.setTaskFieldList(TaskParserImpl.fieldToMap(fields, "field","no", "name"));
        });
        taskDependency.ifPresent(Dependency ->{
            List<Element> taskElementList = Dependency.getChildren("task-dependency");
            List<TaskDependencyImpl> collect = taskElementList.stream().map(element1 -> new TaskDependencyImpl(
                                    element1.getChildText("task-predecessor-id"),
                                    element1.getChildText("task-successor-id"))).collect(Collectors.toList());
            mainXmlBuilder.setTaskDependencyList(collect);
        });
        return mainXmlBuilder.build();
    }

}
