package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskDependencyImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.TaskDependency;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.util.*;

public class TaskParserImpl {

    public static List<Task> parse(Element element, List<TaskDependency> dependencyList) throws JDOMException {
        Stack<Element> tasksStack = new Stack<>();
        List<Task> taskList = new ArrayList<>();
        tasksStack.push(element);

        while (!tasksStack.empty()) {
            Element taskElemFromStack = tasksStack.pop();
            TaskImpl.TaskBuilder taskBuilder = TaskImpl.startBuildTask();

            Optional<Element> fieldListElem = Optional.ofNullable(taskElemFromStack.getChild("field-list"));
            Optional<Element> taskListElem = Optional.ofNullable(taskElemFromStack.getChild("task-list"));
            Optional<Element> taskNotesElem = Optional.ofNullable(taskElemFromStack.getChild("task-notes"));

            Optional<String> parentId = Optional.ofNullable(taskElemFromStack.getAttributeValue("parent-id"));
            Optional<String> taskName = Optional.ofNullable(taskElemFromStack.getChildText("task-name"));
            Optional<String> taskId = Optional.ofNullable(taskElemFromStack.getChildText("task-id"));

            taskBuilder
                .setTaskName(taskName.orElseThrow(()->new JDOMException("Task name is empty!")))
                .setTaskId(taskId.orElseThrow(()-> new JDOMException("Task id is empty!")));

            fieldListElem.ifPresent(fieldList ->
                taskBuilder.setTaskFields(fieldToMap(fieldList, "field","field-no", "field-value"))
            );
            taskNotesElem.ifPresent(notes ->
                taskBuilder.setNotes(StringUtils.normalizeSpace(StringEscapeUtils.unescapeHtml4(notes.getValue())))
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
            dependencyList.add(new TaskDependencyImpl(parentId.orElse("root"),readyTask.getId()));
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
