package com.bestSpringApplication.taskManager.models.study.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Map;

public interface StudySchema extends Serializable {
    @JsonIgnore
    Task getRootTask();
    Map<String,Task> getTasksMap();
}
