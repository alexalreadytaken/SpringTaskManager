package com.bestSpringApplication.taskManager.models.study.implementations;

import com.bestSpringApplication.taskManager.handlers.jsonViews.TaskView;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;


public class TaskImpl implements Task {
    @JsonIgnore
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @JsonView(TaskView.InfoForGraph.class)
    private String id;
    @JsonView(TaskView.InfoForGraph.class)
    private String name;
    @JsonView(TaskView.FullInfo.class)
    private Map<String, String> fields;
    @JsonView(TaskView.FullInfo.class)
    private int duration;
    @JsonView(TaskView.FullInfo.class)
    private String notes;

    @JsonFormat(pattern = DATE_FORMAT)
    @JsonView(TaskView.FullInfo.class)
    private LocalDateTime constraint;
    @JsonFormat(pattern = DATE_FORMAT)
    @JsonView(TaskView.FullInfo.class)
    private LocalDateTime startDate;
    @JsonFormat(pattern = DATE_FORMAT)
    @JsonView(TaskView.FullInfo.class)
    private LocalDateTime endDate;



    public TaskImpl() {}

    public static TaskBuilder startBuildTask(){
        return new TaskImpl().new TaskBuilder();
    }

    public LocalDateTime getConstraint() { return constraint; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public int getDuration() { return duration; }
    public String getName() { return name; }
    public String getId() { return id; }

    public Optional<String> getNotes() { return Optional.ofNullable(notes); }

    public Optional<Map<String, String>> getFields() { return Optional.ofNullable(fields); }

    public void setFields(Map<String, String> fields) { this.fields = fields; }

    public class TaskBuilder{
        TaskBuilder(){}
        public TaskBuilder notes(String notes){
            TaskImpl.this.notes=notes;
            return this;
        }
        public TaskBuilder taskFields(Map<String,String> taskFields) {
            TaskImpl.this.fields = taskFields;
            return this;
        }
        public TaskBuilder taskName(String taskName) {
            TaskImpl.this.name = taskName;
            return this;
        }
        public TaskBuilder taskId(String taskId) {
            TaskImpl.this.id = taskId;
            return this;
        }
        public TaskBuilder taskDuration(int taskDuration) {
            TaskImpl.this.duration = taskDuration;
            return this;
        }
        public TaskBuilder taskConstraint(LocalDateTime taskConstraint) {
            TaskImpl.this.constraint = taskConstraint;
            return this;
        }
        public TaskBuilder taskStartDate(LocalDateTime taskStartDate) {
            TaskImpl.this.startDate = taskStartDate;
            return this;
        }
        public TaskBuilder taskEndDate(LocalDateTime taskEndDate) {
            TaskImpl.this.endDate = taskEndDate;
            return this;
        }
        public TaskImpl build(){
            return TaskImpl.this;
        }
    }
}
