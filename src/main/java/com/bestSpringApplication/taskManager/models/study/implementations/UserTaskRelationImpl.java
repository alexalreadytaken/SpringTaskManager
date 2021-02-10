package com.bestSpringApplication.taskManager.models.study.implementations;

import com.bestSpringApplication.taskManager.handlers.enumConverters.GradeConverter;
import com.bestSpringApplication.taskManager.models.enums.Grade;
import com.bestSpringApplication.taskManager.models.study.interfaces.UserTaskRelation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "user_task_relation")
public class UserTaskRelationImpl implements UserTaskRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer db_id;
    private boolean isFinished;
    private boolean finishConfirmed;
    private String userId;
    private String schemeId;
    private String taskId;
    @Convert(converter = GradeConverter.class)
    private Grade grade;

    public UserTaskRelationImpl(){}

    public UserTaskRelationImpl(boolean isFinished,
                                boolean finishConfirmed,
                                String userId,
                                String schemeId,
                                String taskId,
                                Grade grade) {
        this.isFinished = isFinished;
        this.finishConfirmed = finishConfirmed;
        this.userId = userId;
        this.schemeId = schemeId;
        this.taskId = taskId;
        this.grade = grade;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public String getSchemeId() {
        return schemeId;
    }

    public Integer getDb_id() {
        return db_id;
    }

    public boolean isIsFinished() {
        return isFinished;
    }

    public boolean isFinishConfirmed() {
        return finishConfirmed;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setDb_id(Integer bd_id) {
        this.db_id = bd_id;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setIsFinished(boolean userIsFinishTask) {
        this.isFinished = userIsFinishTask;
    }

    public void setFinishConfirmed(boolean confirmTask) {
        this.finishConfirmed = confirmTask;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
