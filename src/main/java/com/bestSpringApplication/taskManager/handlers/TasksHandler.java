package com.bestSpringApplication.taskManager.handlers;

import com.bestSpringApplication.taskManager.models.Study.implementations.TaskImpl;
import com.bestSpringApplication.taskManager.models.Study.interfaces.Task;
import com.bestSpringApplication.taskManager.models.Study.interfaces.Dependency;
import org.jdom2.JDOMException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class TasksHandler {

    public static Map<Task, List<Task>> makeTasksGraph(Map<String, Task> taskMap, List<Dependency> taskDependencies) throws JDOMException {
        Map<Task,List<Task>> tasksGraph = new HashMap<>();
        Dependency rootTask = taskDependencies.stream().filter(el ->el.getParentId().equals("root"))
            .findFirst().orElseThrow(() -> new JDOMException("Root task element not found!"));
        Stack<Task> taskStack = new Stack<>();

        taskStack.push(taskMap.get(rootTask.getChildId()));

        while (!taskStack.empty()){
            TaskImpl task = ((TaskImpl) taskStack.pop());
            String taskId = task.getId();

            List<String> childesId =
                taskDependencies.stream()
                    .filter(el->el.getParentId().equals(taskId))
                    .map(Dependency::getChildId)
                    .collect(Collectors.toList());

            List<Task> childTasks =
                childesId.stream()
                    .map(taskMap::get)
                    .collect(Collectors.toList());
            tasksGraph.put(task,childTasks);
            childTasks.forEach(taskStack::push);
        }
        return tasksGraph;
    }

    public static void addTaskFields(List<Task> tasks, Map<String, String> schemeFields) {
        tasks.forEach(task -> {
            TaskImpl taskImpl = (TaskImpl) task;
            taskImpl.getFields().ifPresent(taskFields->{
                Map<String,String> completedTaskFields = new HashMap<>();
                for (int i = 0; i < taskFields.size(); i++) {
                    String i0 = String.valueOf(i);
                    String key = schemeFields.get(i0);
                    String value = taskFields.get(i0);
                    completedTaskFields.put(key,value);
                }
                taskImpl.setFields(completedTaskFields);
            });
        });
    }

}
