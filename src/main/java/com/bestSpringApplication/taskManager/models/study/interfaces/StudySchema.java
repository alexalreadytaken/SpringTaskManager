package com.bestSpringApplication.taskManager.models.study.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public interface StudySchema extends Cloneable {
    @JsonIgnore
    Task getRootTask();
    Map<String,Task> getTasksMap();
    StudySchema clone() throws CloneNotSupportedException;
}
