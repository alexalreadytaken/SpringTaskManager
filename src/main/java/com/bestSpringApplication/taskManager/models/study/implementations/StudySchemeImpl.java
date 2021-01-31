package com.bestSpringApplication.taskManager.models.study.implementations;


import com.bestSpringApplication.taskManager.handlers.jsonViews.SchemeView;
import com.bestSpringApplication.taskManager.handlers.parsers.xml.StudySchemaParser;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudyScheme;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.jdom2.Document;
import org.jdom2.JDOMException;

import java.util.List;
import java.util.Map;

public class StudySchemeImpl implements StudyScheme{

    private String name;
    private String id;
    @JsonProperty("values")
    private Map<String, Task> tasksMap;
    @JsonProperty("dependencies")
    private List<Dependency> tasksDependencies;
    @JsonIgnore
    private Map<Task, List<Task>> tasksGraph;

    public StudySchemeImpl(){}

    public StudySchemeImpl(Map<String, Task> tasksMap,
                           Map<Task, List<Task>> tasksGraph,
                           List<Dependency> tasksDependencies) {
        this.tasksMap = tasksMap;
        this.tasksGraph = tasksGraph;
        this.tasksDependencies = tasksDependencies;
    }

    public static StudySchemeImpl parseFromXml(Document document) throws JDOMException {
        return StudySchemaParser.parseSchemaXml(document);
    }

    public boolean isValid(){
        return !tasksMap.isEmpty()&&!tasksGraph.isEmpty();
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

    public List<Dependency> getTasksDependencies() {
        return tasksDependencies;
    }

    public void setTasksDependencies(List<Dependency> tasksDependencies) {
        this.tasksDependencies = tasksDependencies;
    }

    public Map<String, Task> getTasksMap() {
        return tasksMap;
    }

    public void setTasksMap(Map<String, Task> tasksMap) {
        this.tasksMap = tasksMap;
    }

    public Map<Task, List<Task>> getTasksGraph() {
        return tasksGraph;
    }

    public void setTasksGraph(Map<Task, List<Task>> tasksGraph) {
        this.tasksGraph = tasksGraph;
    }

    @Override
    public String toString() {
        return "TasksSchema{" +
            "tasks=" + tasksMap +
            ", taskDependencyList=" + tasksGraph +
            '}';
    }
}
