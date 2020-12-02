package com.bestSpringApplication.taskManager.model.task.classes;

import com.bestSpringApplication.taskManager.model.task.interfaces.TaskDependency;

public class TaskDependencyImpl implements TaskDependency {
    private String TaskProcessorId;
    private String TaskSuccessorId;

    public TaskDependencyImpl(String taskProcessorId, String taskSuccessorId) {
        TaskProcessorId = taskProcessorId;
        TaskSuccessorId = taskSuccessorId;
    }
    public TaskDependencyImpl() {
    }

    public String getTaskProcessorId() {
        return TaskProcessorId;
    }

    public void setTaskProcessorId(String taskProcessorId) {
        TaskProcessorId = taskProcessorId;
    }

    public String getTaskSuccessorId() {
        return TaskSuccessorId;
    }

    public void setTaskSuccessorId(String taskSuccessorId) {
        TaskSuccessorId = taskSuccessorId;
    }

}
