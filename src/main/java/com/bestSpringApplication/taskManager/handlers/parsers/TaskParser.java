package com.bestSpringApplication.taskManager.handlers.parsers;

import com.bestSpringApplication.taskManager.handlers.exceptions.internal.ParseException;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;

import java.util.List;
import java.util.Map;


public interface TaskParser{
    Map<String,AbstractTask> getTasks() throws ParseException;
    List<DependencyWithRelationType> getHierarchicalDependencies() throws ParseException;
}
