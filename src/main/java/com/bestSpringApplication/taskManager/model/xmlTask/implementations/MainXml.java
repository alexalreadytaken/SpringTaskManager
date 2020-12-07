package com.bestSpringApplication.taskManager.model.xmlTask.implementations;


import com.bestSpringApplication.taskManager.model.xmlTask.interfaces.*;

import java.util.List;
import java.util.TimeZone;

public class MainXml {
    private String companyCurrentProject;
    private TimeZone companyTimezone;
    private double companyTaxRate;
    private int companyCalendarId;
    private long creationTime;
    private String version;
    private String type;
    private Task task;
    //todo
    private List<Field> taskFieldList;
    private List<Field> resourceFieldList;
    private List<Field> clientFieldList;
    private List<Calendar> calendarList;
    private List<Resource> resourceList;
    private List<Client> clientList;
    private List<TaskDependency> taskDependencyList;
    private List<Conflict> conflictList;
    private List<Assigment> assigmentList;

    MainXml(){}//fixme for hibernate?

    public static MainXmlBuilder startBuildXml(){
        return new MainXml().new MainXmlBuilder();
    }

    public List<TaskDependency> getTaskDependencyList() { return taskDependencyList; }
    public String getCompanyCurrentProject() { return companyCurrentProject; }
    public List<Field> getResourceFieldList() { return resourceFieldList; }
    public List<Field> getClientFieldList() { return clientFieldList; }
    public List<Assigment> getAssigmentList() { return assigmentList; }
    public TimeZone getCompanyTimezone() { return companyTimezone; }
    public List<Resource> getResourceList() { return resourceList; }
    public List<Conflict> getConflictList() { return conflictList; }
    public int getCompanyCalendarId() { return companyCalendarId; }
    public List<Field> getTaskFieldList() { return taskFieldList; }
    public List<Calendar> getCalendarList() { return calendarList;}
    public double getCompanyTaxRate() { return companyTaxRate; }
    public List<Client> getClientList() { return clientList; }
    public long getCreationTime() { return creationTime; }
    public String getVersion() { return version; }
    public String getType() { return type; }
    public Task getTask() { return task; }


    public class MainXmlBuilder {
        MainXmlBuilder(){}

        public MainXmlBuilder setResourceFieldList(List<Field> resources){
            MainXml.this.resourceFieldList=resources;
            return this;
        }
        public MainXmlBuilder setTask(Task task){
            MainXml.this.task=task;
            return this;
        }
        public MainXmlBuilder setType(String type){
            MainXml.this.type=type;
            return this;
        }
        public MainXmlBuilder setVersion(String version){
            MainXml.this.version=version;
            return this;
        }
        public MainXmlBuilder setCreationTime(long creationTime){
            MainXml.this.creationTime=creationTime;
            return this;
        }
        public MainXmlBuilder setClientList(List<Client> clients){
            MainXml.this.clientList=clients;
            return this;
        }
        public MainXmlBuilder setCompanyTaxRate(double taxRate){
            MainXml.this.companyTaxRate=taxRate;
            return this;
        }
        public MainXmlBuilder setCalendarList(List<Calendar> calendars){
            MainXml.this.calendarList=calendars;
            return this;
        }
        public MainXmlBuilder setTaskFieldList(List<Field> taskFields){
            MainXml.this.taskFieldList=taskFields;
            return this;
        }
        public MainXmlBuilder setCompanyCalendarId(int id){
            MainXml.this.companyCalendarId=id;
            return this;
        }
        public MainXmlBuilder setConflictList(List<Conflict> conflicts){
            MainXml.this.conflictList=conflicts;
            return this;
        }
        public MainXmlBuilder setResourceList(List<Resource> resources){
            MainXml.this.resourceList=resources;
            return this;
        }
        public MainXmlBuilder setCompanyTimezone(TimeZone companyTimezone){
            MainXml.this.companyTimezone=companyTimezone;
            return this;
        }
        public MainXmlBuilder setAssigmentList(List<Assigment> assignments){
            MainXml.this.assigmentList=assignments;
            return this;
        }
        public MainXmlBuilder setClientFieldList(List<Field> clientFields){
            MainXml.this.clientFieldList=clientFields;
            return this;
        }
        public MainXmlBuilder setCompanyCurrentProject(String currentProject){
            MainXml.this.companyCurrentProject=currentProject;
            return this;
        }
        public MainXmlBuilder setTaskDependencyList(List<TaskDependency> dependencies){
            MainXml.this.taskDependencyList=dependencies;
            return this;
        }
        public MainXml build(){
            return MainXml.this;
        }
    }
}
