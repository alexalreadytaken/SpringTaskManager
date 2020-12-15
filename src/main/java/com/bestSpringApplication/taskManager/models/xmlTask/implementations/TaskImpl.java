package com.bestSpringApplication.taskManager.models.xmlTask.implementations;

import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

//@Entity(name = "task")
public class TaskImpl implements Task {
    @JsonIgnore
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

//    @Id
    private String id;
    private Map<String, String> fields; //optional
    private String name;
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime constraint;
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime startDate;
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime endDate;
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime completionDate;
    private int duration;
    private double percentComplete;
    private double workPercentComplete;
    private String notes; //optional

   public TaskImpl() {}

    public static TaskBuilder startBuildTask(){
        return new TaskImpl().new TaskBuilder();
    }

    public double getWorkPercentComplete() { return workPercentComplete; }
    public LocalDateTime getCompletionDate() { return completionDate; }
    public double getPercentComplete() { return percentComplete; }
    public LocalDateTime getConstraint() { return constraint; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public int getDuration() { return duration; }
    public String getName() { return name; }
    public String getId() { return id; }

    public Optional<String> getNotes() { return Optional.ofNullable(notes); }
    //good practice ?
    public Optional<Map<String, String>> getFields() { return Optional.ofNullable(fields); }

    public void setFields(Map<String, String> fields) { this.fields = fields; }

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
