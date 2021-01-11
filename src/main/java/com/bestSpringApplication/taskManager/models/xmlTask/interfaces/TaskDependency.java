package com.bestSpringApplication.taskManager.models.xmlTask.interfaces;

public interface TaskDependency {
    String getTaskChildId();
    String getTaskParentId();
}
