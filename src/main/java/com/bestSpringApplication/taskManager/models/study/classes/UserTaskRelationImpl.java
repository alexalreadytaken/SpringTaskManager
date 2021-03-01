package com.bestSpringApplication.taskManager.models.study.classes;

import com.bestSpringApplication.taskManager.handlers.enumConverters.GradeConverter;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.study.interfaces.UserTaskRelation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity(name = "user_task_relation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskRelationImpl implements UserTaskRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer db_id;
    private boolean isFinished;
    private boolean finishConfirmed;
    private String userId;
    private String schemaId;
    private String taskId;
    @Convert(converter = GradeConverter.class)
    private Grade grade;

}
