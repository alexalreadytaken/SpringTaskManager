package com.bestSpringApplication.taskManager.models.study.abstracts;

import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractTask implements Task {
    private String id;
    private String name;
    private int duration;
    private String notes;
    private boolean theme;
    private boolean completed;
}
