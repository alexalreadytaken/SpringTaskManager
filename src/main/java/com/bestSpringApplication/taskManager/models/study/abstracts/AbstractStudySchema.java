package com.bestSpringApplication.taskManager.models.study.abstracts;

import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Data
@ToString(of = "rootTask")
public abstract class AbstractStudySchema implements Serializable {

    private Map<String, AbstractTask> tasksMap;
    private List<Dependency> dependencies;
    private AbstractTask rootTask;

}
