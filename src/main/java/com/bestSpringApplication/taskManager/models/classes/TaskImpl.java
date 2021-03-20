package com.bestSpringApplication.taskManager.models.classes;

import com.bestSpringApplication.taskManager.handlers.DateHandler;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskImpl extends AbstractTask {

    private Map<String,String> fields;

    @JsonFormat(pattern = DateHandler.SQL_DATE_FORMAT)
    @JsonProperty("actualStart")
    private LocalDateTime startDate;
    @JsonFormat(pattern = DateHandler.SQL_DATE_FORMAT)
    @JsonProperty("actualEnd")
    private LocalDateTime endDate;

    @Builder
    public TaskImpl(String id, String name, int duration, String notes, boolean theme, boolean opened,
                    Map<String, String> fields, LocalDateTime startDate, LocalDateTime endDate){
        super(id, name, duration, notes, theme,opened);

        this.endDate=endDate;
        this.fields=fields;
        this.startDate=startDate;
    }

}

