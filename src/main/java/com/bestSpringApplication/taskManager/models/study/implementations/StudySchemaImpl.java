package com.bestSpringApplication.taskManager.models.study.implementations;


import com.bestSpringApplication.taskManager.handlers.parsers.xml.StudySchemaParser;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jdom2.Document;
import org.jdom2.JDOMException;

import java.util.List;
import java.util.Map;

public class StudySchemaImpl implements StudySchema {

    @JsonProperty("tasks")
    private Map<String, Task> tasksMap;
    private List<Dependency> dependencies;

    public StudySchemaImpl(){}

    public static StudySchema parseFromXml(Document document) throws JDOMException {
        return StudySchemaParser.parseSchemaXml(document);
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    @Override
    public Task getRootTask() {
        return this.tasksMap.get("root");
    }

    @Override
    public Map<String, Task> getTasksMap() {
        return tasksMap;
    }


    // TODO: 2/15/2021
    @Override
    public StudySchema clone() throws CloneNotSupportedException {
        return null;
    }

    public void setTasksMap(Map<String, Task> tasksMap) {
        this.tasksMap = tasksMap;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

}
