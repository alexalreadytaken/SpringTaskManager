package com.bestSpringApplication.taskManager.model.task.implementation;
import com.bestSpringApplication.taskManager.model.task.enums.taskCurrencyUnit;
import com.bestSpringApplication.taskManager.model.task.enums.taskStatus;
import com.bestSpringApplication.taskManager.model.task.interfaces.ProjectBaselineList;
import com.bestSpringApplication.taskManager.model.task.interfaces.ProjectCriticalSlackLimit;
import com.bestSpringApplication.taskManager.model.task.interfaces.ProjectCriticalSlackUnit;
import com.bestSpringApplication.taskManager.model.task.interfaces.Task;
import java.util.Date;
import java.util.List;
public class TaskImpl implements Task {
    private boolean project;
    private PatternImpl pattern;
    private boolean defaultForcedWorking;
    private boolean defaultWorkDriven;
    private boolean defaultFixedUnits;
    private taskStatus taskStatus;
    private String projectManager;
    private String projectCode;
    private long projectBudget;
    private String projectDescription;
    private String projectConstraints;
    private List<ProjectBaselineList> projectBaselineLists;
    private ProjectCriticalSlackLimit projectCriticalSlackLimit;
    private ProjectCriticalSlackUnit projectCriticalSlackUnit;
    private String taskName;
    private String taskId;
    private boolean expandedGantt;
    private List<taskCurrencyUnit> taskCurrencyUnits;
    private String taskConstraint;
    private Date taskStartDate;
    private Date taskEndDate;
    private Date taskCompletionDate;
    private Date taskDuration;
    private long taskPercentComplete;
    private long taskWorkPercentComplete;
    private List<TaskImpl> taskList;
    public TaskImpl(boolean project,
                    PatternImpl pattern,
                    boolean defaultForcedWorking,
                    boolean defaultWorkDriven,
                    boolean defaultFixedUnits,
                    taskStatus taskStatus,
                    String projectManager,
                    String projectCode,
                    long projectBudget,
                    String projectDescription,
                    String projectConstraints,
                    List<ProjectBaselineList> projectBaselineLists,
                    ProjectCriticalSlackLimit projectCriticalSlackLimit,
                    ProjectCriticalSlackUnit projectCriticalSlackUnit,
                    String taskName,
                    String taskId,
                    boolean expandedGantt,
                    List<taskCurrencyUnit> taskCurrencyUnits,
                    String taskConstraint,
                    Date taskStartDate,
                    Date taskEndDate,
                    Date taskCompletionDate,
                    Date taskDuration,
                    long taskPercentComplete,
                    long taskWorkPercentComplete,
                    List<TaskImpl> taskList) {
        this.project = project;
        this.pattern = pattern;
        this.defaultForcedWorking = defaultForcedWorking;
        this.defaultWorkDriven = defaultWorkDriven;
        this.defaultFixedUnits = defaultFixedUnits;
        this.taskStatus = taskStatus;
        this.projectManager = projectManager;
        this.projectCode = projectCode;
        this.projectBudget = projectBudget;
        this.projectDescription = projectDescription;
        this.projectConstraints = projectConstraints;
        this.projectBaselineLists = projectBaselineLists;
        this.projectCriticalSlackLimit = projectCriticalSlackLimit;
        this.projectCriticalSlackUnit = projectCriticalSlackUnit;
        this.taskName = taskName;
        this.taskId = taskId;
        this.expandedGantt = expandedGantt;
        this.taskCurrencyUnits = taskCurrencyUnits;
        this.taskConstraint = taskConstraint;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.taskCompletionDate = taskCompletionDate;
        this.taskDuration = taskDuration;
        this.taskPercentComplete = taskPercentComplete;
        this.taskWorkPercentComplete = taskWorkPercentComplete;
        this.taskList = taskList;
    }

    public boolean isProject() {
        return project;
    }
    public void setProject(boolean project) {
        this.project = project;
    }

    public PatternImpl getPattern() {
        return pattern;
    }
    public void setPattern(PatternImpl pattern) {
        this.pattern = pattern;
    }

    public boolean isDefaultForcedWorking() {
        return defaultForcedWorking;
    }

    public void setDefaultForcedWorking(boolean defaultForcedWorking) {
        this.defaultForcedWorking = defaultForcedWorking;
    }
    public boolean isDefaultWorkDriven() {
        return defaultWorkDriven;
    }
    public void setDefaultWorkDriven(boolean defaultWorkDriven) {
        this.defaultWorkDriven = defaultWorkDriven;
    }

    public boolean isDefaultFixedUnits() {
        return defaultFixedUnits;
    }

    public void setDefaultFixedUnits(boolean defaultFixedUnits) {
        this.defaultFixedUnits = defaultFixedUnits;
    }

    public com.bestSpringApplication.taskManager.model.task.enums.taskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(com.bestSpringApplication.taskManager.model.task.enums.taskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public long getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(long projectBudget) {
        this.projectBudget = projectBudget;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectConstraints() {
        return projectConstraints;
    }

    public void setProjectConstraints(String projectConstraints) {
        this.projectConstraints = projectConstraints;
    }

    public List<ProjectBaselineList> getProjectBaselineLists() {
        return projectBaselineLists;
    }

    public void setProjectBaselineLists(List<ProjectBaselineList> projectBaselineLists) {
        this.projectBaselineLists = projectBaselineLists;
    }

    public ProjectCriticalSlackLimit getProjectCriticalSlackLimit() {
        return projectCriticalSlackLimit;
    }

    public void setProjectCriticalSlackLimit(ProjectCriticalSlackLimit projectCriticalSlackLimit) {
        this.projectCriticalSlackLimit = projectCriticalSlackLimit;
    }

    public ProjectCriticalSlackUnit getProjectCriticalSlackUnit() {
        return projectCriticalSlackUnit;
    }

    public void setProjectCriticalSlackUnit(ProjectCriticalSlackUnit projectCriticalSlackUnit) {
        this.projectCriticalSlackUnit = projectCriticalSlackUnit;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public boolean isExpandedGantt() {
        return expandedGantt;
    }

    public void setExpandedGantt(boolean expandedGantt) {
        this.expandedGantt = expandedGantt;
    }

    public List<taskCurrencyUnit> getTaskCurrencyUnits() {
        return taskCurrencyUnits;
    }

    public void setTaskCurrencyUnits(List<taskCurrencyUnit> taskCurrencyUnits) {
        this.taskCurrencyUnits = taskCurrencyUnits;
    }

    public String getTaskConstraint() {
        return taskConstraint;
    }

    public void setTaskConstraint(String taskConstraint) {
        this.taskConstraint = taskConstraint;
    }

    public Date getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(Date taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public Date getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(Date taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public Date getTaskCompletionDate() {
        return taskCompletionDate;
    }

    public void setTaskCompletionDate(Date taskCompletionDate) {
        this.taskCompletionDate = taskCompletionDate;
    }

    public Date getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(Date taskDuration) {
        this.taskDuration = taskDuration;
    }

    public long getTaskPercentComplete() {
        return taskPercentComplete;
    }

    public void setTaskPercentComplete(long taskPercentComplete) {
        this.taskPercentComplete = taskPercentComplete;
    }

    public long getTaskWorkPercentComplete() {
        return taskWorkPercentComplete;
    }

    public void setTaskWorkPercentComplete(long taskWorkPercentComplete) {
        this.taskWorkPercentComplete = taskWorkPercentComplete;
    }

    public List<TaskImpl> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskImpl> taskList) {
        this.taskList = taskList;
    }
}
