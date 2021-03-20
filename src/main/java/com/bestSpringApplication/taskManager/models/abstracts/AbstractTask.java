package com.bestSpringApplication.taskManager.models.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractTask implements Serializable {
    private String id;
    private String name;
    private int duration;
    private String notes;
    private boolean theme;
    private boolean opened;
}
