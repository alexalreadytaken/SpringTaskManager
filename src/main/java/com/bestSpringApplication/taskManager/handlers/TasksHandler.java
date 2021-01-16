package com.bestSpringApplication.taskManager.handlers;

import com.bestSpringApplication.taskManager.models.Study.implementations.TaskImpl;
import com.bestSpringApplication.taskManager.models.Study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.Study.interfaces.Task;
import org.jdom2.JDOMException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TasksHandler {

    public static Map<Task, List<Task>> makeTasksGraph(Map<String, Task> taskMap, List<Dependency> taskDependencies) throws JDOMException {
        Map<Task,List<Task>> tasksGraph = new HashMap<>();

        String[] strings = taskMap.keySet().toArray(new String[0]);

        for (int i = 0; i < strings.length; i++) {
            int i0 = i;
            Task parentTask = taskMap.get(strings[i]);
            List<Task> childesTasks = taskDependencies.stream()
                .filter(el -> el.getParentId().equals(strings[i0]))
                .map(Dependency::getChildId)
                .filter(el->!el.equals(strings[i0]))
                .map(taskMap::get)
                .collect(Collectors.toList());

            tasksGraph.put(parentTask,childesTasks);
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
