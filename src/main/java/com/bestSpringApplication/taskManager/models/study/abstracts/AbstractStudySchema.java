package com.bestSpringApplication.taskManager.models.study.abstracts;

import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;


@Data
@ToString(of = "rootTask")
public abstract class AbstractStudySchema implements StudySchema {

    private Map<String, Task> tasksMap;
    private List<Dependency> dependencies;
    private Task rootTask;

}
