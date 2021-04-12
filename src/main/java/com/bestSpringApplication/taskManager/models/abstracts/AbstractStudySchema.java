package com.bestSpringApplication.taskManager.models.abstracts;

import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Data
@ToString(of = "rootTask")
@AllArgsConstructor
public abstract class AbstractStudySchema{

    private Map<String, AbstractTask> tasksMap;
    private List<DependencyWithRelationType> dependencies;

    private AbstractTask rootTask;

    @JsonIgnore
    public String getId(){
        return this.rootTask.getName();
    }

    public Stream<AbstractTask> tasksStream(){
        return this.tasksMap
                .values().stream();
    }
}
