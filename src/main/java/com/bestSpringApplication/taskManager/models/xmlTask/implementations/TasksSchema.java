package com.bestSpringApplication.taskManager.models.xmlTask.implementations;


import com.bestSpringApplication.taskManager.handlers.parsers.xml.TasksSchemaParser;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;
import org.jdom2.Document;

import java.util.List;

public class TasksSchema {
    private List<Task> tasks;
    private List<TaskDependencyImpl> taskDependencyList;

    public TasksSchema(){}

    public TasksSchema(List<Task> tasks, List<TaskDependencyImpl> taskDependencyList) {
        this.tasks = tasks;
        this.taskDependencyList = taskDependencyList;
    }
    public static TasksSchema parseFromXml(Document document){
        return TasksSchemaParser.parseSchemaXml(document);
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
