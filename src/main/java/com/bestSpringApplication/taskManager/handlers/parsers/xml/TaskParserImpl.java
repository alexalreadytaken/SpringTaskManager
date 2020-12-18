package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskDependencyImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.TaskDependency;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.w3c.dom.DOMException;

import java.util.*;

public class TaskParserImpl {

    public static List<Task> parse(Element element, List<TaskDependency> dependencyList) {
        Stack<Element> tasksStack = new Stack<>();
        List<Task> taskList = new ArrayList<>();
        tasksStack.push(element);

        while (!tasksStack.empty()) {
            Element taskElemFromStack = tasksStack.pop();
            TaskImpl.TaskBuilder taskBuilder = TaskImpl.startBuildTask();

            Optional<Element> fieldListElem = Optional.ofNullable(taskElemFromStack.getChild("field-list"));
            Optional<Element> taskListElem = Optional.ofNullable(taskElemFromStack.getChild("task-list"));
            Optional<Element> taskNotes = Optional.ofNullable(taskElemFromStack.getChild("task-notes"));

            Optional<String> parentId = Optional.ofNullable(taskElemFromStack.getAttributeValue("parent-id"));
            Optional<String> taskName = Optional.ofNullable(taskElemFromStack.getChildText("task-name"));
            Optional<String> taskId = Optional.ofNullable(taskElemFromStack.getChildText("task-id"));

            taskBuilder
                .setTaskName(taskName.orElse("default-name"))
                .setTaskId(taskId.orElse("default-id"));

            fieldListElem.ifPresent(fieldList ->
                taskBuilder.setTaskFields(fieldToMap(fieldList, "field","field-no", "field-value"))
            );
            taskNotes.ifPresent(value ->
                taskBuilder.setNotes(StringUtils.normalizeSpace(StringEscapeUtils.unescapeHtml4(value.getValue())))
            );
            taskListElem.ifPresent(tasksOpt->{
                Optional<List<Element>> tasks = Optional.ofNullable(tasksOpt.getChildren("task"));
                tasks.ifPresent(tasksListOpt->
                    tasksListOpt.forEach(el->{
                        el.setAttribute("parent-id",taskBuilder.build().getId());
                        tasksStack.push(el);
                    })
                );
            });
            TaskImpl readyTask = taskBuilder.build();
            dependencyList.add(new TaskDependencyImpl(parentId.orElse("root"),readyTask.getId()));//fixme
            taskList.add(readyTask);
        }
        return taskList;
    }
    public static Map<String,String> fieldToMap(Element element, String field, String key, String value){
        List<Element> fields = element.getChildren(field);
        Map<String,String> fieldsMap = new HashMap<>();
        fields.forEach(el ->fieldsMap.put(el.getChildText(key), el.getChildText(value)));
        return fieldsMap;
    }
}
