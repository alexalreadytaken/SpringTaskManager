package com.bestSpringApplication.taskManager.models.classes;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TimedTask extends AbstractTask {

    @JsonProperty("actualStart")
    private long startDate;
    @JsonProperty("actualEnd")
    private long endDate;

    private Map<String,String> fields;

    @Builder
    public TimedTask(String id, String name, int duration, String notes, boolean theme,
                     Map<String, String> fields, long startDate, long endDate){
        super(id, name, duration, notes, theme);

        this.endDate=endDate;
        this.fields=fields;
        this.startDate=startDate;
    }

}

