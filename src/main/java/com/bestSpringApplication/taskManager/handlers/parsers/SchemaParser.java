package com.bestSpringApplication.taskManager.handlers.parsers;

import com.bestSpringApplication.taskManager.handlers.exceptions.internal.ParseException;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;

public interface SchemaParser{
    StudySchema parse(Object parsable) throws ParseException;
}
