package com.bestSpringApplication.taskManager.models.classes;


import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.interfaces.Dependency;

import java.util.List;
import java.util.Map;

public class DefaultStudySchemaImpl extends AbstractStudySchema {

    public DefaultStudySchemaImpl(Map<String, AbstractTask> tasksMap, List<Dependency> dependencies, AbstractTask rootTask) {
        super(tasksMap, dependencies, rootTask);
    }
}
