package com.bestSpringApplication.taskManager.models.xmlTask.implementations;


import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;

import java.util.List;
import java.util.Map;

public class MainXml {
    private List<Task> tasks;
    private List<TaskDependencyImpl> taskDependencyList;

    public MainXml(){}

    public MainXml(List<Task> tasks, List<TaskDependencyImpl> taskDependencyList) {
        this.tasks = tasks;
        this.taskDependencyList = taskDependencyList;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<TaskDependencyImpl> getTaskDependencyList() {
        return taskDependencyList;
    }

    public void setTaskDependencyList(List<TaskDependencyImpl> taskDependencyList) {
        this.taskDependencyList = taskDependencyList;
    }
}
