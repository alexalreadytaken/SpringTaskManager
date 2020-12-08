package com.bestSpringApplication.taskManager.models.xmlTask.implementations;

import com.bestSpringApplication.taskManager.models.xmlTask.enums.CurrencyUnit;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Pattern;
import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TaskImpl implements Task {
    private boolean project;
    private Pattern pattern = new PatternImpl("pattern", Collections.singletonList(new PeriodInDayImpl(true,1212,434524)));
    private Map<String, String> taskFields = new HashMap<>();
    private String taskName;
    private String taskId;
    private LocalDateTime taskConstraint = LocalDateTime.now();
    private LocalDateTime taskStartDate = LocalDateTime.now();
    private LocalDateTime taskEndDate = LocalDateTime.now();
    private LocalDateTime taskCompletionDate = LocalDateTime.now();
    private String taskDuration ="124234";
    private double taskPercentComplete = 1354.4;
    private double taskWorkPercentComplete= 76776.45;
    private int level = 0 ;
    private String notes ="notes";
    private List<TaskImpl> childList = new ArrayList<>();
//    private List<TaskImpl> parentList = new ArrayList<>();

   public TaskImpl() {}//fixme for hibernate?

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
//    public List<TaskImpl> getParentList() { return parentList; }
    public String getTaskDuration() { return taskDuration; }
    public List<TaskImpl> getChildList() {return childList;}
    public Pattern getPattern() { return pattern; }
    public String getTaskName() { return taskName; }
    public boolean isProject() { return project; }
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

    //todo move to new handler ?
    public static LocalDateTime parseDateFromFormat(String date,String format){
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.from(inputFormat.parse(date));
    }

    public class TaskBuilder{
        TaskBuilder(){}
        public TaskBuilder setNotes(String notes){
            TaskImpl.this.notes=notes;
            return this;
        }
        public TaskBuilder setProject(boolean project) {
            TaskImpl.this.project = project;
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
        public TaskBuilder setTaskDuration(String taskDuration) {
            TaskImpl.this.taskDuration = taskDuration;
            return this;
        }
        public TaskBuilder setChildList(List<TaskImpl> taskList) {
            TaskImpl.this.childList = taskList;
            return this;
        }
        /*public TaskBuilder setParentList(List<TaskImpl> taskList) {
            TaskImpl.this.parentList = taskList;
            return this;
        }*/
        public TaskImpl build(){
            return TaskImpl.this;
        }
    }
}
