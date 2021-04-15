package com.bestSpringApplication.taskManager.models.classes;

import lombok.AllArgsConstructor;
import lombok.Data;

// TODO: 4/15/21 more info
@Data
@AllArgsConstructor
public class GroupTaskSummary {
    private String taskId;
    private double percentComplete;
}
