package com.bestSpringApplication.taskManager.utils.enumConverters;

import com.bestSpringApplication.taskManager.models.enums.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<Role,String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        return role==null?null:role.getStrValue();
    }

    @Override
    public Role convertToEntityAttribute(String s) {
        return s==null?null:Role.of(s)
                .orElse(null);
    }
}
