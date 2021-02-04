package com.bestSpringApplication.taskManager.handlers;

import com.bestSpringApplication.taskManager.models.study.enums.Grade;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GradeToDbConverter implements AttributeConverter<Grade,Integer> {

    @Override
    public Integer convertToDatabaseColumn(Grade grade) {
        return grade==null?null:grade.getIntValue();
    }

    @Override
    public Grade convertToEntityAttribute(Integer integer) {
        return integer==null?null:Grade.getInstanceFromInt(integer);
    }
}
