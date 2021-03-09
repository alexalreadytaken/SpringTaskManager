package com.bestSpringApplication.taskManager.models.study.abstracts;

import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private AbstractTask rootTask;

    @JsonIgnore
    public String getUniqueKey(){
        return this.rootTask.getName();
    }
}
