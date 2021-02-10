package com.bestSpringApplication.taskManager.handlers.enumConverters;

import com.bestSpringApplication.taskManager.models.enums.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<Role,String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.getStrValue();
    }

    @Override
    public Role convertToEntityAttribute(String s) {
        return Role.getInstance(s);
    }
}
