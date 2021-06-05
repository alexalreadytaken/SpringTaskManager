package com.bestSpringApplication.taskManager.models.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Summary {
    private String entityId;
    private String groupId;
    private double percentCompleteOfUsers;
    private double averagePercent;
    private double minPercentComplete;
    private double maxPercentComplete;
}
