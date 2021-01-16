package com.bestSpringApplication.taskManager.models.Study.implementations;


import com.bestSpringApplication.taskManager.handlers.parsers.xml.StudySchemaParser;
import com.bestSpringApplication.taskManager.models.Study.interfaces.StudyScheme;
import com.bestSpringApplication.taskManager.models.Study.interfaces.Task;
import com.bestSpringApplication.taskManager.models.Study.interfaces.Dependency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jdom2.Document;
import org.jdom2.JDOMException;

import java.util.List;
import java.util.Map;

public class StudySchemeImpl implements StudyScheme{

    private Map<String,Task> tasksMap;
    @JsonIgnore
    private Map<Task,List<Task>> tasksGraph;
    private List<Dependency> taskDependencies;

    public StudySchemeImpl(){}

    public StudySchemeImpl(Map<String, Task> tasksMap,
                           Map<Task, List<Task>> tasksGraph,
                           List<Dependency> taskDependencies) {
        this.tasksMap = tasksMap;
        this.tasksGraph = tasksGraph;
        this.taskDependencies = taskDependencies;
    }

    public static StudySchemeImpl parseFromXml(Document document) throws JDOMException {
        return StudySchemaParser.parseSchemaXml(document);
    }

    public boolean isValid(){
        return !tasksMap.isEmpty()&&!tasksGraph.isEmpty();
    }

    public List<Dependency> getTaskDependencies() {
        return taskDependencies;
    }

    public void setTaskDependencies(List<Dependency> taskDependencies) {
        this.taskDependencies = taskDependencies;
    }

    public Map<String,Task> getTasksMap() {
        return tasksMap;
    }

    public void setTasksMap(Map<String,Task> tasksMap) {
        this.tasksMap = tasksMap;
    }

    public Map<Task,List<Task>> getTasksGraph() {
        return tasksGraph;
    }

    public void setTasksGraph(Map<Task,List<Task>> tasksGraph) {
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
