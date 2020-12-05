package com.bestSpringApplication.taskManager.model.task.implementation;

import com.bestSpringApplication.taskManager.model.task.interfaces.Calendar;

import java.util.List;

public class CalendarImpl implements Calendar {
    private String calendarType;
    private String calendarName;
    private List<PatternImpl> WeekPatternList;
}
