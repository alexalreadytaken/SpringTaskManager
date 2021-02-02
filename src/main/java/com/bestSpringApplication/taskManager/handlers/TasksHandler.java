package com.bestSpringApplication.taskManager.handlers;

import com.bestSpringApplication.taskManager.models.study.implementations.TaskImpl;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksHandler {

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
