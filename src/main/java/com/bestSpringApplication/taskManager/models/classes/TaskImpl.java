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
@ToString(of={},callSuper = true)
public class TaskImpl extends AbstractTask {

    private Map<String,String> fields;

    @JsonProperty("actualStart")
    private long startDate;
    @JsonProperty("actualEnd")
    private long endDate;

    @Builder
    public TaskImpl(String id, String name, int duration, String notes, boolean theme, boolean opened,
                    Map<String, String> fields, long startDate, long endDate){
        super(id, name, duration, notes, theme,opened);

        this.endDate=endDate;
        this.fields=fields;
        this.startDate=startDate;
    }

}

