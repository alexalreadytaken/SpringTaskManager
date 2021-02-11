package com.bestSpringApplication.taskManager.models.study.implementations;


import com.bestSpringApplication.taskManager.handlers.jsonView.SchemasView;
import com.bestSpringApplication.taskManager.handlers.parsers.xml.StudySchemaParser;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudyScheme;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.jdom2.Document;
import org.jdom2.JDOMException;

import java.util.List;
import java.util.Map;

public class StudySchemeImpl implements StudyScheme{

    @JsonView(SchemasView.OverviewInfo.class)
    private String name;
    @JsonView(SchemasView.OverviewInfo.class)
    private String id;
    @JsonProperty("tasks")
    @JsonView(SchemasView.FullInfo.class)
    private Map<String, Task> tasksMap;
    @JsonView(SchemasView.FullInfo.class)
    private List<Dependency> dependencies;

    public StudySchemeImpl(){}

    public static StudySchemeImpl parseFromXml(Document document) throws JDOMException {
        return StudySchemaParser.parseSchemaXml(document);
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
