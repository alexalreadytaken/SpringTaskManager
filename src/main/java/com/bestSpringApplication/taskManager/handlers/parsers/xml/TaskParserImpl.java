package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.handlers.DateHandler;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskDependencyImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.TaskImpl;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jdom2.Element;

import java.util.*;
import java.util.stream.Collectors;

public class TaskParserImpl {

    @Deprecated
    //работает корректно только без xml`ных dependency
    public static void depthFirst(List<TaskDependencyImpl> dependencies,List<Task> tasks){
        Stack<TaskImpl> tasksStack = new Stack<>();
        List<TaskImpl> taskImplList = (List<TaskImpl>) (List<?>) tasks;
        TaskImpl rootTask =taskImplList.get(0);
        rootTask.setLevel(0);
        tasksStack.push(rootTask);
        while (!tasksStack.empty()){
            TaskImpl taskFromStack = tasksStack.pop();
            int level = taskFromStack.getLevel();
            String taskId = taskFromStack.getId();
            for (int i = 0; i < level; i++) {
                System.out.print("    ");
            }
            Set<String> parentsId = dependencies.stream()
                .filter(el->el.getTaskChildId().equals(taskId))
                .map(TaskDependencyImpl::getTaskParentId).collect(Collectors.toSet());
            /*
            List<TaskImpl> parentList = taskImplList.stream().filter(el->parentsId.contains(el.getTaskId()))
                .collect(Collectors.toList());*/

            Set<String> childesId = dependencies.stream()
                .filter(el->el.getTaskParentId().equals(taskId))
                .map(TaskDependencyImpl::getTaskChildId).collect(Collectors.toSet());

            List<TaskImpl> childList = taskImplList.stream().filter(el->childesId.contains(el.getId()))
                .collect(Collectors.toList());

            System.out.println(
                taskFromStack.getName()+ "(" + taskFromStack.getId() + ")"+
                    " <--"+parentsId.toString());

            childList.forEach(el -> {
                int some = level+1;
                el.setLevel(some);
                tasksStack.push(el);
            });
        }
    }
    public static List<Task> parse(Element element, List<TaskDependencyImpl> dependencyList){
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
                .setTaskId(taskId.orElse("default-id"))
                //test
                .setTaskDuration(Integer.parseInt(taskElemFromStack.getChildText("task-duration")))
                .setTaskStartDate(DateHandler.parseDateFromFormat(taskElemFromStack.getChildText("task-start-date"),"dd-MM-yyyy, HH:mm:ss"))
                .setTaskEndDate(DateHandler.parseDateFromFormat(taskElemFromStack.getChildText("task-end-date"),"dd-MM-yyyy, HH:mm:ss"));


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
        depthFirst(dependencyList,taskList);
        return taskList;
    }
    public static Map<String,String> fieldToMap(Element element, String field, String key, String value){
        List<Element> fields = element.getChildren(field);
        Map<String,String> fieldsMap = new HashMap<>();
        fields.forEach(el ->fieldsMap.put(el.getChildText(key), el.getChildText(value)));
        return fieldsMap;
    }
}
