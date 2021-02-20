package com.bestSpringApplication.taskManager.handlers.parsers;

import com.bestSpringApplication.taskManager.handlers.exceptions.internal.ParseException;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;

import java.util.List;


public interface TaskParser{
    List<Task> parse(Object parsable) throws ParseException;
}
