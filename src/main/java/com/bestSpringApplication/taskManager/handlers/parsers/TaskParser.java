package com.bestSpringApplication.taskManager.handlers.parsers;

import com.bestSpringApplication.taskManager.handlers.exceptions.internal.ParseException;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractTask;

import java.util.List;


public interface TaskParser{
    List<AbstractTask> parse(Object parsable) throws ParseException;
}
