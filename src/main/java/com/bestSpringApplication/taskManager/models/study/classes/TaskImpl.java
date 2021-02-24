package com.bestSpringApplication.taskManager.models.study.classes;

import com.bestSpringApplication.taskManager.handlers.DateHandler;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractTask;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskImpl extends AbstractTask {

    private Map<String,String> fields;
    private String parentId;
    private List<String> childrenId;

    @JsonFormat(pattern = DateHandler.SQL_DATE_FORMAT)
    @JsonProperty("actualStart")
    private LocalDateTime startDate;
    @JsonFormat(pattern = DateHandler.SQL_DATE_FORMAT)
    @JsonProperty("actualEnd")
    private LocalDateTime endDate;

    @Builder
    public TaskImpl(String id,String name,int duration,String notes,boolean theme,boolean opened,
                    Map<String,String> fields,String parentId,List<String> childrenId,LocalDateTime startDate,LocalDateTime endDate){
        super(id, name, duration, notes, theme,opened);

        this.childrenId=childrenId;
        this.endDate=endDate;
        this.fields=fields;
        this.parentId=parentId;
        this.startDate=startDate;
    }


}

