package com.bestSpringApplication.taskManager.models.classes;

import com.bestSpringApplication.taskManager.models.interfaces.Dependency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Data
@ToString(of = "rootTask")
@AllArgsConstructor
public class StudySchema {

    private Map<String, StudyTask> tasksMap;
    private List<Dependency> dependencies;
    private StudyTask rootTask;

    @JsonIgnore
    public String getId(){
        return this.rootTask.getId();
    }

    @JsonIgnore
    public List<DependencyWithRelationType> getDependenciesWithRelationType(){
        return this.dependencies.stream()
                .filter(DependencyWithRelationType.class::isInstance)
                .map(DependencyWithRelationType.class::cast)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public Stream<StudyTask> tasksStream(){
        return this.tasksMap.values().stream();
    }
}
