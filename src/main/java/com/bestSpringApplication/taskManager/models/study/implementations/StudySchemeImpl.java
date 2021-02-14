package com.bestSpringApplication.taskManager.models.study.implementations;


import com.bestSpringApplication.taskManager.handlers.parsers.xml.StudySchemaParser;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudyScheme;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jdom2.Document;
import org.jdom2.JDOMException;

import java.util.List;
import java.util.Map;

public class StudySchemeImpl implements StudyScheme{

    @JsonProperty("tasks")
    private Map<String, Task> tasksMap;
    private List<Dependency> dependencies;

    public StudySchemeImpl(){}

    public static StudyScheme parseFromXml(Document document) throws JDOMException {
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

    public void setTasksMap(Map<String, Task> tasksMap) {
        this.tasksMap = tasksMap;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

}
