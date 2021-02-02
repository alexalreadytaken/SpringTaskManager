package com.bestSpringApplication.taskManager.models.study.implementations;

import com.bestSpringApplication.taskManager.models.customId.IdRelation;
import com.bestSpringApplication.taskManager.models.study.interfaces.UserTaskRelation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserTaskRelationImpl implements UserTaskRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bd_id;
//    private IdRelation userRelation;
//    private IdRelation taskRelation;
    private boolean userIsFinishTask;
    private boolean confirmTask;
    private String grade;

    public UserTaskRelationImpl(){}

    public UserTaskRelationImpl(Integer bd_id,
                                IdRelation userRelation,
                                IdRelation taskRelation,
                                boolean userIsFinishTask,
                                boolean confirmTask,
                                String grade) {
        this.bd_id = bd_id;
//        this.userRelation = userRelation;
//        this.taskRelation = taskRelation;
        this.userIsFinishTask = userIsFinishTask;
        this.confirmTask = confirmTask;
        this.grade = grade;
    }

    public Integer getBd_id() {
        return bd_id;
    }

    public boolean isUserIsFinishTask() {
        return userIsFinishTask;
    }

    public boolean isConfirmTask() {
        return confirmTask;
    }

    public String getGrade() {
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

    public void setUserIsFinishTask(boolean userIsFinishTask) {
        this.userIsFinishTask = userIsFinishTask;
    }

    public void setConfirmTask(boolean confirmTask) {
        this.confirmTask = confirmTask;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
