package com.bestSpringApplication.taskManager.models.xmlTask.implementations;


import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class MainXml {
    private Task task;
    private Map<String,String> taskFieldList;
    private List<TaskDependencyImpl> taskDependencyList = new ArrayList<>();

    public MainXml(){}//fixme for hibernate?

    public static MainXmlBuilder startBuildXml(){
        return new MainXml().new MainXmlBuilder();
    }
    public List<TaskDependencyImpl> getTaskDependencyList() { return taskDependencyList; }
    public Map<String,String> getTaskFieldList() { return taskFieldList; }
    public Task getTask() { return task; }


    public class MainXmlBuilder {

        MainXmlBuilder(){}
        public MainXmlBuilder setTask(Task task){
            MainXml.this.task=task;
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
