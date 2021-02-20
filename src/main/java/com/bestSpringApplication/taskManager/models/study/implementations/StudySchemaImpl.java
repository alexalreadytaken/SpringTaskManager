package com.bestSpringApplication.taskManager.models.study.implementations;


import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "rootTask")
public class StudySchemaImpl implements StudySchema {

    @JsonProperty("tasks")
    private Map<String,Task> tasksMap;
    private List<Dependency> dependencies;
    private Task rootTask;

}
