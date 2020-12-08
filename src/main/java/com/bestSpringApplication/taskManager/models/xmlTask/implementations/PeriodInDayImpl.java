package com.bestSpringApplication.taskManager.models.xmlTask.implementations;

public class PeriodInDayImpl {
    private boolean periodType;
    private long PeriodStartDate;
    private long PeriodEndDate;

    public PeriodInDayImpl(boolean periodType, long periodStartDate, long periodEndDate) {
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

    public long getPeriodStartDate() {
        return PeriodStartDate;
    }

    public void setPeriodStartDate(long periodStartDate) {
        PeriodStartDate = periodStartDate;
    }

    public long getPeriodEndDate() {
        return PeriodEndDate;
    }

    public void setPeriodEndDate(long periodEndDate) {
        PeriodEndDate = periodEndDate;
    }
}
