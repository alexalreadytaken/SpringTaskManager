package com.bestSpringApplication.taskManager.models.classes;

import com.bestSpringApplication.taskManager.models.enums.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class Summary {
    private String entityId;
    private double percentComplete;
    private double averageGrade;
    private Map<Grade,Long> countByGrade;
}