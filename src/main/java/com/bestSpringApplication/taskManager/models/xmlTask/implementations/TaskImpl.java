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

    private Map<String, String> fields = new HashMap<>();
    private String name;
    private String id;
    @JsonFormat(pattern = responseDateFormat)
    private LocalDateTime constraint = LocalDateTime.now();
    @JsonFormat(pattern = responseDateFormat)
    private LocalDateTime startDate = LocalDateTime.now();
    @JsonFormat(pattern = responseDateFormat)
    private LocalDateTime endDate = LocalDateTime.now();
    @JsonFormat(pattern = responseDateFormat)
    private LocalDateTime completionDate = LocalDateTime.now();
    private int duration =0;
    private double percentComplete = 0.0;
    private double workPercentComplete = 0.0;
    @JsonIgnore
    private int level;
    private String notes ="empty";

   public TaskImpl() {}

    public static TaskBuilder startBuildTask(){
        return new TaskImpl().new TaskBuilder();
    }

    public double getWorkPercentComplete() { return workPercentComplete; }
    public LocalDateTime getCompletionDate() { return completionDate; }
    public double getPercentComplete() { return percentComplete; }
    public LocalDateTime getConstraint() { return constraint; }
    public Map<String, String> getFields() { return fields; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public int getDuration() { return duration; }
    public String getName() { return name; }
    public String getId() { return id; }
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
            TaskImpl.this.fields = taskFields;
            return this;
        }
        public TaskBuilder setTaskName(String taskName) {
            TaskImpl.this.name = taskName;
            return this;
        }
        public TaskBuilder setTaskId(String taskId) {
            TaskImpl.this.id = taskId;
            return this;
        }
        public TaskBuilder setTaskDuration(int taskDuration) {
            TaskImpl.this.duration = taskDuration;
            return this;
        }
        public TaskBuilder setTaskConstraint(LocalDateTime taskConstraint) {
            TaskImpl.this.constraint = taskConstraint;
            return this;
        }
        public TaskBuilder setTaskStartDate(LocalDateTime taskStartDate) {
            TaskImpl.this.startDate = taskStartDate;
            return this;
        }
        public TaskBuilder setTaskEndDate(LocalDateTime taskEndDate) {
            TaskImpl.this.endDate = taskEndDate;
            return this;
        }
        public TaskBuilder setTaskCompletionDate(LocalDateTime taskCompletionDate) {
            TaskImpl.this.completionDate = taskCompletionDate;
            return this;
        }
        public TaskImpl build(){
            return TaskImpl.this;
        }
    }
}
