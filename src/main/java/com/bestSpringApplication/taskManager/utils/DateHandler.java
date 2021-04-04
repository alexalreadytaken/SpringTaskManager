package com.bestSpringApplication.taskManager.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateHandler {

    public static final String SQL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime parseDateFromFormat(String date, String format){
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.from(inputFormat.parse(date));
    }

    public static long parseDateToLongFromFormat(String date, String format){
        LocalDateTime localDateTime = parseDateFromFormat(date, format);
        return ZonedDateTime
                .of(localDateTime,ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }
}
