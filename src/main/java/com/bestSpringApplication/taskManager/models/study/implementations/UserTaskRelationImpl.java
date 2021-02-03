package com.bestSpringApplication.taskManager.models.study.implementations;

import com.bestSpringApplication.taskManager.models.idRelation.IdRelation;
import com.bestSpringApplication.taskManager.models.idRelation.IdRelationImpl;
import com.bestSpringApplication.taskManager.models.study.enums.Grade;
import com.bestSpringApplication.taskManager.models.study.interfaces.UserTaskRelation;

import javax.persistence.*;

@Entity(name = "user_task_relation")
public class UserTaskRelationImpl implements UserTaskRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "db_id")
    private IdRelationImpl userRelation;
    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "db_id")
    private IdRelationImpl taskRelation;
    private boolean isFinished;
    private boolean finishConfirmed;
    private Grade grade;

    public UserTaskRelationImpl(){}

    public UserTaskRelationImpl(IdRelationImpl userRelation,
                                IdRelationImpl taskRelation,
                                boolean isFinished,
                                boolean finishConfirmed,
                                Grade grade) {
        this.id = id;
        this.userRelation = userRelation;
        this.taskRelation = taskRelation;
        this.isFinished = isFinished;
        this.finishConfirmed = finishConfirmed;
        this.grade = grade;
    }

    public Integer getId() {
        return id;
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

    @Override
    public IdRelation getUserRelation() {
        return userRelation;
    }

    @Override
    public IdRelation getTaskRelation() {
        return taskRelation;
    }

    public void setId(Integer bd_id) {
        this.id = bd_id;
    }

    public void setUserRelation(IdRelationImpl userRelation) {
        this.userRelation = userRelation;
    }

    public void setTaskRelation(IdRelationImpl taskRelation) {
        this.taskRelation = taskRelation;
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
