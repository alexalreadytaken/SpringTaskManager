package com.bestSpringApplication.taskManager.models.xmlTask.implementations;

import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TaskImpl implements Task {
    @JsonIgnore
    private static final String responseDateFormat = "yyyy-MM-dd HH:mm:ss";

    private Map<String, String> taskFields = new HashMap<>();
    private String taskName;
    private String taskId;
    @JsonFormat(pattern = responseDateFormat)
    private LocalDateTime taskConstraint = LocalDateTime.now();
    @JsonFormat(pattern = responseDateFormat)
    private LocalDateTime taskStartDate = LocalDateTime.now();
    @JsonFormat(pattern = responseDateFormat)
    private LocalDateTime taskEndDate = LocalDateTime.now();
    @JsonFormat(pattern = responseDateFormat)
    private LocalDateTime taskCompletionDate = LocalDateTime.now();
    private int taskDuration =0;
    private double taskPercentComplete = 0.0;
    private double taskWorkPercentComplete= 0.0;
    @JsonIgnore
    private int level;
    private String notes ="empty";

   public TaskImpl() {}

    public static TaskBuilder startBuildTask(){
        return new TaskImpl().new TaskBuilder();
    }

    public double getTaskWorkPercentComplete() { return taskWorkPercentComplete; }
    public LocalDateTime getTaskCompletionDate() { return taskCompletionDate; }
    public double getTaskPercentComplete() { return taskPercentComplete; }
    public LocalDateTime getTaskConstraint() { return taskConstraint; }
    public Map<String, String> getTaskFields() { return taskFields; }
    public LocalDateTime getTaskStartDate() { return taskStartDate; }
    public LocalDateTime getTaskEndDate() { return taskEndDate; }
    public int getTaskDuration() { return taskDuration; }
    public String getTaskName() { return taskName; }
    public String getTaskId() { return taskId; }
    public String getNotes() { return notes; }




    //odnorazoviy
    public int getLevel() {
        int a = level;
        level = 0;
        return a;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public class TaskBuilder{
        TaskBuilder(){}
        public TaskBuilder setNotes(String notes){
            TaskImpl.this.notes=notes;
            return this;
        }
        public TaskBuilder setTaskFields(Map<String,String> taskFields) {
            TaskImpl.this.taskFields = taskFields;
            return this;
        }
        public TaskBuilder setTaskName(String taskName) {
            TaskImpl.this.taskName = taskName;
            return this;
        }
        public TaskBuilder setTaskId(String taskId) {
            TaskImpl.this.taskId = taskId;
            return this;
        }
        public TaskBuilder setTaskDuration(int taskDuration) {
            TaskImpl.this.taskDuration = taskDuration;
            return this;
        }
        public TaskBuilder setTaskConstraint(LocalDateTime taskConstraint) {
            TaskImpl.this.taskConstraint = taskConstraint;
            return this;
        }
        public TaskBuilder setTaskStartDate(LocalDateTime taskStartDate) {
            TaskImpl.this.taskStartDate = taskStartDate;
            return this;
        }
        public TaskBuilder setTaskEndDate(LocalDateTime taskEndDate) {
            TaskImpl.this.taskEndDate = taskEndDate;
            return this;
        }
        public TaskBuilder setTaskCompletionDate(LocalDateTime taskCompletionDate) {
            TaskImpl.this.taskCompletionDate = taskCompletionDate;
            return this;
        }
        public TaskImpl build(){
            return TaskImpl.this;
        }
    }
}
