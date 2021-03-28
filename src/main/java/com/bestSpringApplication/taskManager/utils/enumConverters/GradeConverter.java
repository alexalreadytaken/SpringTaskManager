package com.bestSpringApplication.taskManager.utils.enumConverters;

import com.bestSpringApplication.taskManager.models.enums.Grade;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GradeConverter implements AttributeConverter<Grade,Integer> {

    @Override
    public Integer convertToDatabaseColumn(Grade grade) {
        return grade==null?null:grade.getIntValue();
    }

    @Override
    public Grade convertToEntityAttribute(Integer integer) {
        return integer==null?null:Grade.of(integer);
    }
}
