package com.bestSpringApplication.taskManager.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Grade {
    ONE(1),TWO(2),THREE(3),FOUR(4),FIVE(5);

    @Getter(onMethod = @__({ @JsonValue }))
    private final int intValue;

    private static final Map<Integer,Grade> VALUES = new HashMap<>();

    static {
        for (Grade el: values()) VALUES.put(el.getIntValue(),el);
    }

    Grade(int intValue){
        this.intValue=intValue;
    }

    public static Grade of(Integer intValue){
        return VALUES.get(intValue);
    }
}
