package com.bestSpringApplication.taskManager.utils.enumConverters;

import com.bestSpringApplication.taskManager.models.enums.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StatusConverter implements AttributeConverter<Status,String> {
    @Override
    public String convertToDatabaseColumn(Status status) {
        return status==null?null:status.getName();
    }

    @Override
    public Status convertToEntityAttribute(String s) {
        return s==null?null:Status.of(s);
    }
}
