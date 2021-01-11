package com.bestSpringApplication.taskManager.models.xmlTask.implementations;


import com.bestSpringApplication.taskManager.handlers.parsers.xml.TasksSchemaParser;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.TaskDependency;
import org.jdom2.Document;
import org.jdom2.JDOMException;

import java.util.List;
import java.util.Map;

public class TasksSchema {

    private Map<String,Task> tasksMap;
    private Map<Task,List<Task>> tasksGraph;
    private List<TaskDependency> taskDependencies;

    public TasksSchema(){}

    public TasksSchema(Map<String, Task> tasksMap,
                       Map<Task, List<Task>> tasksGraph,
                       List<TaskDependency> taskDependencies) {
        this.tasksMap = tasksMap;
        this.tasksGraph = tasksGraph;
        this.taskDependencies = taskDependencies;
    }

    public static TasksSchema parseFromXml(Document document) throws JDOMException {
        return TasksSchemaParser.parseSchemaXml(document);
    }

    public boolean isValid(){
        return !tasksMap.isEmpty()&&!tasksGraph.isEmpty();
    }

    public List<TaskDependency> getTaskDependencies() {
        return taskDependencies;
    }

    public void setTaskDependencies(List<TaskDependency> taskDependencies) {
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
