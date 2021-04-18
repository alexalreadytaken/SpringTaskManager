package com.bestSpringApplication.taskManager.models.abstracts;

import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.interfaces.Dependency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Data
@ToString(of = "rootTask")
@AllArgsConstructor
public abstract class AbstractStudySchema{

    private Map<String, AbstractTask> tasksMap;
    private List<Dependency> dependencies;

    private AbstractTask rootTask;

    @JsonIgnore
    public String getId(){
        return this.rootTask.getName();
    }

    public List<DependencyWithRelationType> getDependenciesWithRelationType(){
        return this.dependencies
                .stream()
                .filter(DependencyWithRelationType.class::isInstance)
                .map(DependencyWithRelationType.class::cast)
                .collect(Collectors.toList());
    }

    public Stream<AbstractTask> tasksStream(){
        return this.tasksMap
                .values().stream();
    }
}
