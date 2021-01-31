package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.handlers.DateHandler;
import com.bestSpringApplication.taskManager.models.study.implementations.DependencyImpl;
import com.bestSpringApplication.taskManager.models.study.implementations.TaskImpl;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.util.*;

public class TaskParser {

    public static List<Task> parseFromXml(Element element, List<Dependency> dependencyList) throws JDOMException {
        Stack<Element> tasksStack = new Stack<>();
        List<Task> taskList = new ArrayList<>();
        tasksStack.push(element);

        while (!tasksStack.empty()) {
            Element taskElemFromStack = tasksStack.pop();
            TaskImpl.TaskBuilder taskBuilder = TaskImpl.startBuildTask();

            Optional<Element> fieldListElem = Optional.ofNullable(taskElemFromStack.getChild("field-list"));
            Optional<Element> taskListElem = Optional.ofNullable(taskElemFromStack.getChild("task-list"));
            Optional<Element> taskNotesElem = Optional.ofNullable(taskElemFromStack.getChild("task-notes"));

            String startDate = taskElemFromStack.getChildText("task-start-date");
            String endDate = taskElemFromStack.getChildText("task-end-date");

            Optional<String> parentId = Optional.ofNullable(taskElemFromStack.getAttributeValue("parent-id"));
            Optional<String> taskName = Optional.ofNullable(taskElemFromStack.getChildText("task-name"));
            Optional<String> taskId = Optional.ofNullable(taskElemFromStack.getChildText("task-id"));

            taskBuilder
                .taskName(taskName.orElseThrow(()->new JDOMException("Task name is empty!")))
                .taskId(taskId.orElseThrow(()-> new JDOMException("Task id is empty!")))
                .taskStartDate(DateHandler.parseDateFromFormat(startDate,"dd-MM-yyyy, HH:mm:ss"))
                .taskEndDate(DateHandler.parseDateFromFormat(endDate,"dd-MM-yyyy, HH:mm:ss"));

            fieldListElem.ifPresent(fieldList ->
                taskBuilder.taskFields(fieldToMap(fieldList, "field","field-no", "field-value"))
            );
            taskNotesElem.ifPresent(notes ->
                taskBuilder.notes(StringUtils.normalizeSpace(StringEscapeUtils.unescapeHtml4(notes.getValue())))
            );
            taskListElem.ifPresent(tasksOpt->{
                taskBuilder.isTheme(true);
                Optional<List<Element>> tasks = Optional.ofNullable(tasksOpt.getChildren("task"));
                tasks.ifPresent(tasksListOpt->
                    tasksListOpt.forEach(el->{
                        el.setAttribute("parent-id",taskBuilder.build().getId());
                        tasksStack.push(el);
                    })
                );
            });
            TaskImpl readyTask = taskBuilder.build();
            String id = readyTask.getId();
            dependencyList.add(new DependencyImpl(parentId.orElse(id), id));
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
