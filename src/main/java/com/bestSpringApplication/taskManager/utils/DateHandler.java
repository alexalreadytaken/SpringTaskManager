package com.bestSpringApplication.taskManager.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateHandler {

    public static LocalDateTime parseDateFromFormat(String date, String format){
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.from(inputFormat.parse(date));
    }

    public static long parseDateToLongFromFormat(String date, String format){
        LocalDateTime localDateTime;
        if (date!=null&&format!=null) {
            localDateTime = parseDateFromFormat(date, format);
        }else {
            localDateTime = LocalDateTime.now();
        }
        return ZonedDateTime
                .of(localDateTime,ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }
}
