package com.bestSpringApplication.taskManager.models.xmlTask.interfaces;

public interface TaskDependency {

    String getTaskParentId();
    String getTaskChildId();
}
