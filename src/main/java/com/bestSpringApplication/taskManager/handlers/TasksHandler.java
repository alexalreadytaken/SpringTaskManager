package com.bestSpringApplication.taskManager.handlers;

import com.bestSpringApplication.taskManager.models.study.implementations.TaskImpl;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;

import java.util.List;
import java.util.Map;

public class TasksHandler {

    public static void addTaskFields(List<Task> tasks, Map<String, String> schemeFields) {
        tasks.forEach(task -> {
            TaskImpl taskImpl = (TaskImpl) task;
            Map<String, String> taskFields = taskImpl.getFields();
            for (int i = 0; taskFields!=null && i <  taskFields.size(); i++) {
                String i0 = String.valueOf(i);
                String key = schemeFields.get(i0);
                String value = taskFields.remove(i0);
                taskFields.put(key,value);
            }
        });
    }


}


