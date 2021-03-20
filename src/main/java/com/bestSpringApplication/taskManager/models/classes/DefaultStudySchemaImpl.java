package com.bestSpringApplication.taskManager.models.classes;


import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;

import java.util.List;
import java.util.Map;

public class DefaultStudySchemaImpl extends AbstractStudySchema {

    public DefaultStudySchemaImpl(Map<String, AbstractTask> tasksMap, List<DependencyWithRelationType> dependencies, AbstractTask rootTask) {
        super(tasksMap, dependencies, rootTask);
    }
}
