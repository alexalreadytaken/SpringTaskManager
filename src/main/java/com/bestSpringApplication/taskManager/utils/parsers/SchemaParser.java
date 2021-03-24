package com.bestSpringApplication.taskManager.utils.parsers;

import com.bestSpringApplication.taskManager.utils.exceptions.internal.ParseException;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;

public interface SchemaParser{
    AbstractStudySchema parse(Object parsable) throws ParseException;
}
