package com.bestSpringApplication.taskManager.handlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHandler {
    public static LocalDateTime parseDateFromFormat(String date, String format){
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.from(inputFormat.parse(date));
    }
}
