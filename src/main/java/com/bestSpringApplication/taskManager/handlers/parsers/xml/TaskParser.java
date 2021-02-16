package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.handlers.DateHandler;
import com.bestSpringApplication.taskManager.models.study.implementations.TaskImpl;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.util.*;

public class TaskParser {

    public static List<Task> parseFromXml(Element element) throws JDOMException {
        Stack<Element> tasksStack = new Stack<>();
        List<Task> taskList = new ArrayList<>();
        tasksStack.push(element);

        while (!tasksStack.empty()) {
            Element taskElemFromStack = tasksStack.pop();
            TaskImpl.TaskImplBuilder taskBuilder = TaskImpl.builder();

            String taskName = Optional.ofNullable(taskElemFromStack.getChildText("task-name"))
                    .orElseThrow(()-> new JDOMException("Task id is empty!"));
            String taskId = Optional.ofNullable(taskElemFromStack.getChildText("task-id"))
                    .orElseThrow(()->new JDOMException("Task name is empty!"));

            Optional<Element> fieldListElem = Optional.ofNullable(taskElemFromStack.getChild("field-list"));
            Optional<Element> taskListElem = Optional.ofNullable(taskElemFromStack.getChild("task-list"));
            Optional<Element> taskNotesElem = Optional.ofNullable(taskElemFromStack.getChild("task-notes"));
            Optional<String> parentId = Optional.ofNullable(taskElemFromStack.getAttributeValue("parent-id"));

            String startDate = taskElemFromStack.getChildText("task-start-date");
            String endDate = taskElemFromStack.getChildText("task-end-date");


            List<String> childrenId = new ArrayList<>();

            fieldListElem.ifPresent(fieldList ->
                    taskBuilder.fields(fieldToMap(fieldList, "field","field-no", "field-value"))
            );
            taskNotesElem.ifPresent(notes ->
                    taskBuilder.notes(StringUtils.normalizeSpace(StringEscapeUtils.unescapeHtml4(notes.getValue())))
            );
            taskListElem.ifPresent(tasksOpt->{
                taskBuilder.isTheme(true);
                Optional<List<Element>> tasks = Optional.ofNullable(tasksOpt.getChildren("task"));
                tasks.ifPresent(tasksListOpt->
                        tasksListOpt.forEach(el->{
                            String taskElemId = el.getChildText("task-id");
                            childrenId.add(taskElemId);
                            el.setAttribute("parent-id",taskId);
                            tasksStack.push(el);
                        })
                );
            });
            String optimizedName = StringUtils.normalizeSpace(taskName).replaceAll(" ", "_");

            taskBuilder
                    .name(optimizedName)
                    .id(taskId)
                    .parentsId(parentId.orElse(null))
                    .childrenId(childrenId)
                    .startDate(DateHandler.parseDateFromFormat(startDate,"dd-MM-yyyy, HH:mm:ss"))
                    .endDate(DateHandler.parseDateFromFormat(endDate,"dd-MM-yyyy, HH:mm:ss"));

            taskList.add(taskBuilder.build());
        }
        taskList.remove(0);
        return taskList;
    }
    public static Map<String,String> fieldToMap(Element element, String field, String key, String value){
        List<Element> fields = element.getChildren(field);
        Map<String,String> fieldsMap = new HashMap<>();
        fields.forEach(el ->fieldsMap.put(el.getChildText(key), el.getChildText(value)));
        return fieldsMap;
    }
}
