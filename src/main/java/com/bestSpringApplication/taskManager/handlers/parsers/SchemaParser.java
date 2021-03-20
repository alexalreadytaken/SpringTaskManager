package com.bestSpringApplication.taskManager.handlers.parsers;

import com.bestSpringApplication.taskManager.handlers.exceptions.internal.ParseException;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;

public interface SchemaParser{
    AbstractStudySchema parse(Object parsable) throws ParseException;
}
