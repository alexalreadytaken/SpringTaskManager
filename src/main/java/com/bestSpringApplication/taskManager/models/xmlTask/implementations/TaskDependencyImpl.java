package com.bestSpringApplication.taskManager.models.xmlTask.implementations;

import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.TaskDependency;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity(name = "task-dependencies")
public class TaskDependencyImpl implements TaskDependency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskParentId;
    private String taskChildId;
    public TaskDependencyImpl(String taskParentId, String taskChildId) {
        this.taskParentId = taskParentId;
        this.taskChildId = taskChildId;
    }
    public TaskDependencyImpl() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTaskChildId() {
        return taskChildId;
    }
    public String getTaskParentId() {
        return taskParentId;
    }
    public void setTaskChildId(String taskChildId) {
        this.taskChildId = taskChildId;
    }
    public void setTaskParentId(String taskParentId) {
        this.taskParentId = taskParentId;
    }

    @Override
    public String toString() {
        return "{" +
                "TaskProcessorId='" + taskParentId + '\'' +
                ", TaskSuccessorId='" + taskChildId + '\'' +
                '}';
    }
}
