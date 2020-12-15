package com.bestSpringApplication.taskManager.models.xmlTask.implementations;


import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainXml {
    private List<Task> tasks;
    private Map<String,String> taskFieldList;
    private List<TaskDependencyImpl> taskDependencyList = new ArrayList<>();

    public MainXml(){}

    public static MainXmlBuilder startBuildXml(){
        return new MainXml().new MainXmlBuilder();
    }
    public List<TaskDependencyImpl> getTaskDependencyList() { return taskDependencyList; }
    public Map<String,String> getTaskFieldList() { return taskFieldList; }
    public List<Task> getTasks() { return tasks; }

    public class MainXmlBuilder {

        MainXmlBuilder(){}
        public MainXmlBuilder setTasks(List<Task> tasks) {
            MainXml.this.tasks = tasks;
            return this;
        }
        public MainXmlBuilder setTaskFieldList(Map<String,String> taskFields){
            MainXml.this.taskFieldList=taskFields;
            return this;
        }
        public MainXmlBuilder setTaskDependencyList(List<TaskDependencyImpl> dependencies){
            MainXml.this.taskDependencyList=dependencies;
            return this;
        }
        public MainXml build(){
            return MainXml.this;
        }
    }
}
