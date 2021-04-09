package com.bestSpringApplication.taskManager.models.abstracts;

import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Data
@ToString(of = "rootTask")
@AllArgsConstructor
public abstract class AbstractStudySchema implements Serializable {

    private Map<String, AbstractTask> tasksMap;
    private List<DependencyWithRelationType> dependencies;

    private AbstractTask rootTask;

    @JsonIgnore
    public String getId(){
        return this.rootTask.getName();
    }
}
