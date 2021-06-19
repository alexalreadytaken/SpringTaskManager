package com.bestSpringApplication.taskManager.models.classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyTask {
    private String id;
    private String schemaId;
    private String name;
    private int duration;
    private String notes;
    private boolean theme;

    @JsonProperty("actualStart")
    private long startDate;
    @JsonProperty("actualEnd")
    private long endDate;

    private Map<String,String> fields;
}
