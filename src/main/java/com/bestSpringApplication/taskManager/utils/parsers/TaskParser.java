package com.bestSpringApplication.taskManager.utils.parsers;

import com.bestSpringApplication.taskManager.utils.exceptions.internal.ParseException;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;

import java.util.List;
import java.util.Map;


public interface TaskParser{
    Map<String,AbstractTask> getTasks();
    List<DependencyWithRelationType> getHierarchicalDependencies();
}
