package com.bestSpringApplication.taskManager.model.task.classes;


import com.bestSpringApplication.taskManager.model.task.interfaces.*;

import java.util.List;
import java.util.TimeZone;

public class MainInformXml {
    private String companyCurrentProject;
    private TimeZone companyTimezone;
    private double companyTaxRate;
    private int companyCalendarId;
    private long creationTime;
    private String version;
    private String type;
    private Task task;

    private List<Field> taskFieldList;
    private List<Field> resourceFieldList;
    private List<Field> clientFieldList;
    private List<Calendar> calendarList;
    private List<Resource> resourceList;
    private List<Client> clientList;
    private List<Dependency> taskDependencyList;
    private List<Conflict> conflictList;
    private List<Assigment> assigmentList;

    MainInformXml(){}

    public static MainXmlBuilder newXML(){
        return new MainInformXml().new MainXmlBuilder();
    }

    public List<Dependency> getTaskDependencyList() { return taskDependencyList; }
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
            MainInformXml.this.resourceFieldList=resources;
            return this;
        }
        public MainXmlBuilder setTask(Task task){
            MainInformXml.this.task=task;
            return this;
        }
        public MainXmlBuilder setType(String type){
            MainInformXml.this.type=type;
            return this;
        }
        public MainXmlBuilder setVersion(String version){
            MainInformXml.this.version=version;
            return this;
        }
        public MainXmlBuilder setCreationTime(long creationTime){
            MainInformXml.this.creationTime=creationTime;
            return this;
        }
        public MainXmlBuilder setClientList(List<Client> clients){
            MainInformXml.this.clientList=clients;
            return this;
        }
        public MainXmlBuilder setCompanyTaxRate(double taxRate){
            MainInformXml.this.companyTaxRate=taxRate;
            return this;
        }
        public MainXmlBuilder setCalendarList(List<Calendar> calendars){
            MainInformXml.this.calendarList=calendars;
            return this;
        }
        public MainXmlBuilder setTaskFieldList(List<Field> taskFields){
            MainInformXml.this.taskFieldList=taskFields;
            return this;
        }
        public MainXmlBuilder setCompanyCalendarId(int id){
            MainInformXml.this.companyCalendarId=id;
            return this;
        }
        public MainXmlBuilder setConflictList(List<Conflict> conflicts){
            MainInformXml.this.conflictList=conflicts;
            return this;
        }
        public MainXmlBuilder setResourceList(List<Resource> resources){
            MainInformXml.this.resourceList=resources;
            return this;
        }
        public MainXmlBuilder setCompanyTimezone(TimeZone companyTimezone){
            MainInformXml.this.companyTimezone=companyTimezone;
            return this;
        }
        public MainXmlBuilder setAssigmentList(List<Assigment> assignments){
            MainInformXml.this.assigmentList=assignments;
            return this;
        }
        public MainXmlBuilder setClientFieldList(List<Field> clientFields){
            MainInformXml.this.clientFieldList=clientFields;
            return this;
        }
        public MainXmlBuilder setCompanyCurrentProject(String currentProject){
            MainInformXml.this.companyCurrentProject=currentProject;
            return this;
        }
        public MainXmlBuilder setTaskDependencyList(List<Dependency> dependencies){
            MainInformXml.this.taskDependencyList=dependencies;
            return this;
        }
        public MainInformXml build(){
            return MainInformXml.this;
        }
    }
}
