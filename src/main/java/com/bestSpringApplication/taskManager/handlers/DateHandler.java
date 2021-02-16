package com.bestSpringApplication.taskManager.handlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHandler {

    public static final String MYSQL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime parseDateFromFormat(String date, String format){
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.from(inputFormat.parse(date));
    }
}
