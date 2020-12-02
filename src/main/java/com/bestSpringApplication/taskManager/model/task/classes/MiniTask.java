package com.bestSpringApplication.taskManager.model.task.classes;
import com.bestSpringApplication.taskManager.model.task.interfaces.Field;
import java.util.Date;
import java.util.List;

public class MiniTask {
    private String taskName;
    private String taskId;
    private Date taskStartDate;
    private Date taskEndDate;
    private Date taskCompletionDate;
    private String taskDuration;
    private List<Field> fieldList;

    public MiniTask(String taskName,
                    String taskId,
                    Date taskStartDate,
                    Date taskEndDate,
                    Date taskCompletionDate,
                    String taskDuration,
                    List<Field> fieldList) {
        this.taskName = taskName;
        this.taskId = taskId;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.taskCompletionDate = taskCompletionDate;
        this.taskDuration = taskDuration;
        this.fieldList = fieldList;
    }

    public MiniTask() {
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

    public String getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(String taskDuration) {
        this.taskDuration = taskDuration;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }
}
