package com.bestSpringApplication.taskManager.models.entities;

import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.utils.enumConverters.StatusConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "user_task_states")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer db_id;
    private String userId;
    private String schemaId;
    private String taskId;
    @Min(0) @Max(1)
    private double percentComplete;
    @Convert(converter = StatusConverter.class)
    private Status status;
}
