package com.bestSpringApplication.taskManager.models.xmlTask.implementations;

import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Calendar;

import java.util.List;

public class CalendarImpl implements Calendar {
    private String calendarType;
    private String calendarName;
    private List<PatternImpl> WeekPatternList;
}
