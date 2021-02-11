package com.bestSpringApplication.taskManager.models.study.implementations;

import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class TaskImpl implements Task {
    @JsonIgnore
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private String id;
    private String name;
    private Map<String, String> fields;
    private int duration;
    private String notes;
    private boolean isTheme = false;
    private String parentsId;
    private List<String> childrenId;

    @JsonFormat(pattern = DATE_FORMAT)
    @JsonProperty("actualStart")
    private LocalDateTime startDate;
    @JsonFormat(pattern = DATE_FORMAT)
    @JsonProperty("actualEnd")
    private LocalDateTime endDate;
    private LocalDateTime constraint;


    public TaskImpl() {}

    public static TaskBuilder startBuildTask(){
        return new TaskImpl().new TaskBuilder();
    }

    public Optional<Map<String, String>> getFields() { return Optional.ofNullable(fields); }
    public Optional<String> getNotes() { return Optional.ofNullable(notes); }

    public LocalDateTime getConstraint() { return constraint; }
    public List<String> getChildrenId() { return childrenId; }
    public LocalDateTime getStartDate() { return startDate; }
    public String getParentsId() { return parentsId; }
    public LocalDateTime getEndDate() { return endDate; }
    public int getDuration() { return duration; }
    public boolean isTheme() { return isTheme; }
    public String getName() { return name; }
    public String getId() { return id; }

    public void setFields(Map<String, String> fields) { this.fields = fields; }

    public class TaskBuilder{
        TaskBuilder(){}
        public TaskBuilder notes(String notes){
            TaskImpl.this.notes=notes;
            return this;
        }
        public TaskBuilder parentsId(String parentId){
            TaskImpl.this.parentsId=parentId;
            return this;
        }
        public TaskBuilder childrenId(List<String> childrenId){
            TaskImpl.this.childrenId=childrenId;
            return this;
        }
        public TaskBuilder isTheme(boolean isTheme){
            TaskImpl.this.isTheme=isTheme;
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
