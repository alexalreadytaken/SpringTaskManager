package com.bestSpringApplication.taskManager.models.study.interfaces;

import java.util.Map;

public interface StudyScheme {
    Task getRootTask();
    Map<String,Task> getTasksMap();
}
