package com.bestSpringApplication.taskManager.model.task.implementation;

import com.bestSpringApplication.taskManager.model.task.interfaces.Pattern;

import java.util.List;

public class PatternImpl implements Pattern {
    private String PatternName;
    private List<PeriodInDayImpl> periodInDay;

    public PatternImpl(String patternName, List<PeriodInDayImpl> periodInDay) {
        PatternName = patternName;
        this.periodInDay = periodInDay;
    }

    public PatternImpl() {
    }

    public String getPatternName() {
        return PatternName;
    }

    public void setPatternName(String patternName) {
        PatternName = patternName;
    }

    public List<PeriodInDayImpl> getPeriodInDay() {
        return periodInDay;
    }

    public void setPeriodInDay(List<PeriodInDayImpl> periodInDay) {
        this.periodInDay = periodInDay;
    }
}
