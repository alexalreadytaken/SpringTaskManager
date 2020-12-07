package com.bestSpringApplication.taskManager.model.xmlTask.implementations;

import com.bestSpringApplication.taskManager.model.xmlTask.enums.CurrencyUnit;
import com.bestSpringApplication.taskManager.model.xmlTask.interfaces.Pattern;
import com.bestSpringApplication.taskManager.model.xmlTask.interfaces.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TaskImpl implements Task {
    private boolean project;
    private Pattern pattern;
    private boolean defaultForcedWorking;
    private boolean defaultWorkDriven;
    private boolean defaultFixedUnits;
    private String projectManager;
    private String projectCode;
    private long projectBudget;
    private String projectDescription;
    private String projectConstraints;
    private String taskName;
    private String taskId;
    private boolean expandedGantt;
    private CurrencyUnit currencyUnits;
    private LocalDateTime taskConstraint;
    private LocalDateTime taskStartDate;
    private LocalDateTime taskEndDate;
    private LocalDateTime taskCompletionDate;
    private String taskDuration;
    private double taskPercentComplete;
    private double taskWorkPercentComplete;
    private int level;
    private List<TaskImpl> taskList;

    TaskImpl() {}//fixme for hibernate?

    public static TaskBuilder startBuildTask(){
        return new TaskImpl().new TaskBuilder();
    }

    public CurrencyUnit getCurrencyUnits() { return currencyUnits; }
    public double getTaskWorkPercentComplete() { return taskWorkPercentComplete; }
    public LocalDateTime getTaskCompletionDate() { return taskCompletionDate; }
    public boolean isDefaultForcedWorking() { return defaultForcedWorking; }
    public double getTaskPercentComplete() { return taskPercentComplete; }
    public String getProjectConstraints() { return projectConstraints; }
    public String getProjectDescription() { return projectDescription; }
    public LocalDateTime getTaskConstraint() { return taskConstraint; }
    public boolean isDefaultFixedUnits() { return defaultFixedUnits; }
    public boolean isDefaultWorkDriven() { return defaultWorkDriven; }
    public LocalDateTime getTaskStartDate() { return taskStartDate; }
    public LocalDateTime getTaskEndDate() { return taskEndDate; }
    public String getProjectManager() { return projectManager; }
    public boolean isExpandedGantt() { return expandedGantt; }
    public long getProjectBudget() { return projectBudget; }
    public String getTaskDuration() { return taskDuration; }
    public String getProjectCode() { return projectCode; }
    public List<TaskImpl> getTaskList() {return taskList;}
    public Pattern getPattern() { return pattern; }
    public String getTaskName() { return taskName; }
    public boolean isProject() { return project; }
    public String getTaskId() { return taskId; }

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
    private static LocalDateTime parseDateFromFormat(String date,String format){
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.from(inputFormat.parse(date));
    }

    public class TaskBuilder{
        TaskBuilder(){}
        public TaskBuilder setTaskCurrencyUnits(CurrencyUnit CurrencyUnits) {
            TaskImpl.this.currencyUnits = CurrencyUnits;
            return this;
        }
        public TaskBuilder setProject(boolean project) {
            TaskImpl.this.project = project;
            return this;
        }
        public TaskBuilder setPattern(Pattern pattern) {
            TaskImpl.this.pattern = pattern;
            return this;
        }
        public TaskBuilder setDefaultForcedWorking(boolean defaultForcedWorking) {
            TaskImpl.this.defaultForcedWorking = defaultForcedWorking;
            return this;
        }
        public TaskBuilder setDefaultWorkDriven(boolean defaultWorkDriven) {
            TaskImpl.this.defaultWorkDriven = defaultWorkDriven;
            return this;
        }
        public TaskBuilder setDefaultFixedUnits(boolean defaultFixedUnits) {
            TaskImpl.this.defaultFixedUnits = defaultFixedUnits;
            return this;
        }
        public TaskBuilder setProjectManager(String projectManager) {
            TaskImpl.this.projectManager = projectManager;
            return this;
        }
        public TaskBuilder setProjectCode(String projectCode) {
            TaskImpl.this.projectCode = projectCode;
            return this;
        }
        public TaskBuilder setProjectBudget(long projectBudget) {
            TaskImpl.this.projectBudget = projectBudget;
            return this;
        }
        public TaskBuilder setProjectDescription(String projectDescription) {
            TaskImpl.this.projectDescription = projectDescription;
            return this;
        }
        public TaskBuilder setProjectConstraints(String projectConstraints) {
            TaskImpl.this.projectConstraints = projectConstraints;
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
        public TaskBuilder setExpandedGantt(boolean expandedGantt) {
            TaskImpl.this.expandedGantt = expandedGantt;
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
        public TaskBuilder setTaskDuration(String taskDuration) {
            TaskImpl.this.taskDuration = taskDuration;
            return this;
        }
        public TaskBuilder setTaskPercentComplete(double taskPercentComplete) {
            TaskImpl.this.taskPercentComplete = taskPercentComplete;
            return this;
        }
        public TaskBuilder setTaskWorkPercentComplete(double taskWorkPercentComplete) {
            TaskImpl.this.taskWorkPercentComplete = taskWorkPercentComplete;
            return this;
        }
        public TaskBuilder setTaskList(List<TaskImpl> taskList) {
            TaskImpl.this.taskList = taskList;
            return this;
        }
        public TaskImpl build(){
            return TaskImpl.this;
        }
    }
}
