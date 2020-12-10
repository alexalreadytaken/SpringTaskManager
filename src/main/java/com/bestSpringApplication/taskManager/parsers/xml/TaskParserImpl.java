package com.bestSpringApplication.taskManager.parsers.xml;

import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskDependencyImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jdom2.Element;

import java.util.*;
import java.util.stream.Collectors;

public class TaskParserImpl {

    @Deprecated
    public static void depthFirst(TaskImpl task){
        Stack<TaskImpl> stack = new Stack<>();
        task.setLevel(0);
        stack.push(task);
        while (!stack.empty()){
            TaskImpl task0 = stack.pop();
            int a = task0.getLevel();
            for (int i = 0; i < a; i++) {
                System.out.print("    ");
            }
            System.out.println(
                            task0.getTaskName()+ "(" + task0.getTaskId() + ")" +
                            " <--"+task0.getTaskDependencyList().toString());
            List<TaskImpl> taskList = task0.getChildList();
            Collections.reverse(taskList);
            taskList.forEach(el -> {
                int some = a+1;
                el.setLevel(some);
                stack.push(el);
            });
        }
    }
    public static TaskImpl parse(Element element,List<TaskDependencyImpl> relativesList){
        TaskImpl.TaskBuilder taskBuilder = TaskImpl.startBuildTask();
        Optional<String> taskDuration = Optional.ofNullable(element.getChildText("task-duration"));
        Optional<Element> fieldListElem = Optional.ofNullable(element.getChild("field-list"));
        Optional<Element> taskListElem = Optional.ofNullable(element.getChild("task-list"));
        Optional<Element> taskNotes = Optional.ofNullable(element.getChild("task-notes"));

        taskBuilder
                .setTaskName(element.getChildText("task-name"))
                .setTaskId(element.getChildText("task-id"))
                .setTaskDuration(taskDuration.orElse("none"));

        fieldListElem.ifPresent(fieldList -> {
            taskBuilder.setTaskFields(fieldToMap(fieldList, "field","field-no", "field-value"));
        });
        taskNotes.ifPresent(value ->
                taskBuilder.setNotes(StringUtils.normalizeSpace(StringEscapeUtils.unescapeHtml4(value.getValue())))
        );
        for(TaskDependencyImpl taskDependency : relativesList){
            if (taskDependency.getTaskSuccessorId().equals(element.getChildText("task-id"))){
                taskBuilder.setDependency(taskDependency);
            }
        }

        taskListElem.ifPresent(tasksOpt->{
            Optional<List<Element>> tasks = Optional.ofNullable(tasksOpt.getChildren("task"));
            tasks.ifPresent(tasksListOpt->{
                List<TaskImpl> childTasks = tasksListOpt.stream()
                        .map(el->parse(el, relativesList))
                        .collect(Collectors.toList());
                taskBuilder.setChildList(childTasks);
            });
        });
        return taskBuilder.build();
    }

    public static Map<String,String> fieldToMap(Element element, String field, String key, String value){
        List<Element> fields = element.getChildren(field);
        Map<String,String> fieldsMap = new HashMap<>();
        fields.forEach(el ->fieldsMap.put(el.getChildText(key), el.getChildText(value)));
        return fieldsMap;
    }
}
