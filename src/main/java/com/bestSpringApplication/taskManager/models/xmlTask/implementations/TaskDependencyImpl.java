package com.bestSpringApplication.taskManager.models.xmlTask.implementations;

import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.TaskDependency;

public class TaskDependencyImpl implements TaskDependency {
    private String taskParentId;
    private String taskChildId;
    public TaskDependencyImpl(String taskParentId, String taskChildId) {
        this.taskParentId = taskParentId;
        this.taskChildId = taskChildId;
    }
    public TaskDependencyImpl() {
    }
    public String getTaskParentId() {
        return taskParentId;
    }
    public void setTaskParentId(String taskParentId) {
        this.taskParentId = taskParentId;
    }
    public String getTaskChildId() {
        return taskChildId;
    }
    public void setTaskChildId(String taskChildId) {
        this.taskChildId = taskChildId;
    }

    @Override
    public String toString() {
        return "{" +
                "TaskProcessorId='" + taskParentId + '\'' +
                ", TaskSuccessorId='" + taskChildId + '\'' +
                '}';
    }
}
