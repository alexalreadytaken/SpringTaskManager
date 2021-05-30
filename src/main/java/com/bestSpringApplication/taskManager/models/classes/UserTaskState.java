package com.bestSpringApplication.taskManager.models.classes;

import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.utils.enumConverters.GradeConverter;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.utils.enumConverters.StatusConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_task_state")
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
    @Convert(converter = GradeConverter.class)
    private Grade grade;
    @Convert(converter = StatusConverter.class)
    private Status status;
}
