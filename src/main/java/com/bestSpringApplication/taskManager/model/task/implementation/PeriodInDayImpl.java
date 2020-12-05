package com.bestSpringApplication.taskManager.model.task.implementation;

import java.util.Date;

public class PeriodInDayImpl {
    private boolean periodType;
    private Date PeriodStartDate;
    private Date PeriodEndDate;

    public PeriodInDayImpl(boolean periodType, Date periodStartDate, Date periodEndDate) {
        this.periodType = periodType;
        PeriodStartDate = periodStartDate;
        PeriodEndDate = periodEndDate;
    }

    public PeriodInDayImpl() {
    }

    public boolean isPeriodType() {
        return periodType;
    }

    public void setPeriodType(boolean periodType) {
        this.periodType = periodType;
    }

    public Date getPeriodStartDate() {
        return PeriodStartDate;
    }

    public void setPeriodStartDate(Date periodStartDate) {
        PeriodStartDate = periodStartDate;
    }

    public Date getPeriodEndDate() {
        return PeriodEndDate;
    }

    public void setPeriodEndDate(Date periodEndDate) {
        PeriodEndDate = periodEndDate;
    }
}
