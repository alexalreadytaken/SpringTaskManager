package com.bestSpringApplication.taskManager.utils.parsers;

import com.bestSpringApplication.taskManager.models.classes.StudySchema;

public interface SchemaParser{
    StudySchema parse(Object parsable);
}
