package com.bestSpringApplication.taskManager.models.enums;

import com.bestSpringApplication.taskManager.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Grade {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    @Getter(onMethod = @__({ @JsonValue }))
    private final int intValue;

    private static final Map<Integer,Grade> VALUES = new HashMap<>(Grade.values().length);

    static {
        for (Grade el: values()) VALUES.put(el.getIntValue(),el);
    }

    Grade(int intValue){
        this.intValue=intValue;
    }

    public static Optional<Grade> of(Integer intValue){
        return Optional.ofNullable(VALUES.get(intValue));
    }

    public static Optional<Grade> of(String intValue){
        if (StringUtils.isInteger(intValue)){
            int i = Integer.parseInt(intValue);
            return Optional.ofNullable(VALUES.get(i));
        }
        return Optional.empty();
    }
}
