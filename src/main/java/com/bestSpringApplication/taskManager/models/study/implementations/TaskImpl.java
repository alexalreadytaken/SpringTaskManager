package com.bestSpringApplication.taskManager.models.study.implementations;

import com.bestSpringApplication.taskManager.handlers.DateHandler;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class TaskImpl implements Task {

    private String id;
    private String name;
    private Map<String, String> fields;
    private int duration;
    private String notes;
    private boolean theme = false;
    private String parentId;
    private List<String> childrenId;

    @JsonFormat(pattern = DateHandler.SQL_DATE_FORMAT)
    @JsonProperty("actualStart")
    private LocalDateTime startDate;
    @JsonFormat(pattern = DateHandler.SQL_DATE_FORMAT)
    @JsonProperty("actualEnd")
    private LocalDateTime endDate;
    private LocalDateTime constraint;

}
