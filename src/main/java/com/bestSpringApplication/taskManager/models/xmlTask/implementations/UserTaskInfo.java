package com.bestSpringApplication.taskManager.models.xmlTask.implementations;
import com.bestSpringApplication.taskManager.models.user.User;

import java.time.LocalDateTime;

public class UserTaskInfo {
    private Integer id;
    private User user;
    private Task task;
    private LocalDateTime startDate;
    private LocalDateTime completeDate;
    private boolean userIsFinishTask;
    private boolean confirmTask;
    private String grade;

    public UserTaskInfo(
            Integer id,
            User user,
            Task task,
            LocalDateTime startDate,
            LocalDateTime completeDate,
            boolean userIsFinishTask,
            boolean confirmTask,
            String grade) {
        this.id = id;
        this.user = user;
        this.task = task;
        this.startDate = startDate;
        this.completeDate = completeDate;
        this.userIsFinishTask = userIsFinishTask;
        this.confirmTask = confirmTask;
        this.grade = grade;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Task getTask() {
        return task;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getCompleteDate() {
        return completeDate;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setCompleteDate(LocalDateTime completeDate) {
        this.completeDate = completeDate;
    }

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