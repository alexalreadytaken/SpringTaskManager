package com.bestSpringApplication.taskManager.models.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractTask{
    private String id;
    private String name;
    private int duration;
    private String notes;
    private boolean theme;
}
