package com.bestSpringApplication.taskManager.models.xmlTask.implementations;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class UserTaskInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    private String taskId;
    private LocalDateTime startDate;
    private LocalDateTime completeDate;
    private boolean userIsFinishTask;
    private boolean confirmTask;
    private String grade;

    public UserTaskInfo(){}

    public static UserTaskInfoBuilder newRelation(){
        return new UserTaskInfo().new UserTaskInfoBuilder();
    }

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTaskId() {
        return taskId;
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

    public class UserTaskInfoBuilder{

        UserTaskInfoBuilder(){}

        public UserTaskInfoBuilder userId(String user) {
            UserTaskInfo.this.userId = user;
            return this;
        }

        public UserTaskInfoBuilder taskId(String taskId) {
            UserTaskInfo.this.taskId = taskId;
            return this;
        }

        public UserTaskInfoBuilder startDate(LocalDateTime startDate) {
            UserTaskInfo.this.startDate = startDate;
            return this;
        }

        public UserTaskInfoBuilder completeDate(LocalDateTime completeDate) {
            UserTaskInfo.this.completeDate = completeDate;
            return this;
        }

        public UserTaskInfoBuilder UserIsFinishTask(boolean userIsFinishTask) {
            UserTaskInfo.this.userIsFinishTask = userIsFinishTask;
            return this;
        }

        public UserTaskInfoBuilder confirmTask(boolean confirmTask) {
            UserTaskInfo.this.confirmTask = confirmTask;
            return this;
        }

        public UserTaskInfoBuilder grade(String grade) {
            UserTaskInfo.this.grade = grade;
            return this;
        }
        public UserTaskInfo build(){
            return UserTaskInfo.this;
        }
    }
}
