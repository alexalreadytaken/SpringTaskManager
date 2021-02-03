package com.bestSpringApplication.taskManager.models.study.implementations;

import com.bestSpringApplication.taskManager.models.idRelation.IdRelation;
import com.bestSpringApplication.taskManager.models.idRelation.IdRelationImpl;
import com.bestSpringApplication.taskManager.models.study.enums.Grade;
import com.bestSpringApplication.taskManager.models.study.interfaces.UserTaskRelation;

import javax.persistence.*;

@Entity
public class UserTaskRelationImpl implements UserTaskRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bd_id;
//    private IdRelationImpl userRelation;
//    private IdRelationImpl taskRelation;
    private boolean isFinished;
    private boolean finishConfirmed;
    private Grade grade;

    public UserTaskRelationImpl(){}

    public UserTaskRelationImpl(Integer bd_id,
                                IdRelationImpl userRelation,
                                IdRelationImpl taskRelation,
                                boolean isFinished,
                                boolean finishConfirmed,
                                Grade grade) {
        this.bd_id = bd_id;
//        this.userRelation = userRelation;
//        this.taskRelation = taskRelation;
        this.isFinished = isFinished;
        this.finishConfirmed = finishConfirmed;
        this.grade = grade;
    }

    public Integer getBd_id() {
        return bd_id;
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
        return null;
    }

    @Override
    public IdRelation getTaskRelation() {
        return null;
    }

    public void setBd_id(Integer bd_id) {
        this.bd_id = bd_id;
    }

//    public void setUserRelation(IdRelation userRelation) {
//        this.userRelation = userRelation;
//    }

//    public void setTaskRelation(IdRelation taskRelation) {
//        this.taskRelation = taskRelation;
//    }

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
