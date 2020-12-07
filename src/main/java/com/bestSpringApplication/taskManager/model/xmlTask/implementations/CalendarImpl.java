package com.bestSpringApplication.taskManager.model.xmlTask.implementations;

import com.bestSpringApplication.taskManager.model.xmlTask.interfaces.Calendar;

import java.util.List;

public class CalendarImpl implements Calendar {
    private String calendarType;
    private String calendarName;
    private List<PatternImpl> WeekPatternList;
}
