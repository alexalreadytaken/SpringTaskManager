package com.bestSpringApplication.taskManager.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Grade {
    IN_WORK(0),ONE(1),TWO(2),THREE(3),FOUR(4),FIVE(5);

    private final int intValue;

    Grade(int intValue){
        this.intValue=intValue;
    }

    @JsonValue
    public int getIntValue(){
        return this.intValue;
    }

    public static Grade getInstanceFromInt(Integer intValue){
        return Arrays.stream(Grade.values())
            .filter(el->el.getIntValue()==intValue)
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
