package com.bestSpringApplication.taskManager.models.xmlTask.implementations;


import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainXml {
    private List<Task> tasks;
    private Map<String,String> taskFieldList;
    private List<TaskDependencyImpl> taskDependencyList;

    public MainXml(){}

    public MainXml(List<Task> tasks, Map<String, String> taskFieldList, List<TaskDependencyImpl> taskDependencyList) {
        this.tasks = tasks;
        this.taskFieldList = taskFieldList;
        this.taskDependencyList = taskDependencyList;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Map<String, String> getTaskFieldList() {
        return taskFieldList;
    }

    public void setTaskFieldList(Map<String, String> taskFieldList) {
        this.taskFieldList = taskFieldList;
    }

    public List<TaskDependencyImpl> getTaskDependencyList() {
        return taskDependencyList;
    }

    public void setTaskDependencyList(List<TaskDependencyImpl> taskDependencyList) {
        this.taskDependencyList = taskDependencyList;
    }
}
