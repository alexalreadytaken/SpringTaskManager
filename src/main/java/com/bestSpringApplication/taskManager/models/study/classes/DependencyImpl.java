package com.bestSpringApplication.taskManager.models.study.classes;

import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DependencyImpl implements Dependency{
    private String id0;
    private String id1;
}
