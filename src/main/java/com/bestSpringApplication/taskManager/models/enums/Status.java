package com.bestSpringApplication.taskManager.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    IN_WORK("in_work"),FINISHED("finished"),DEBT("debt"),NOT_CONFIRMED("not_confirmed");

    private static final Map<String,Status> VALUES = new HashMap<>();

    static {
        for(Status el: values()) VALUES.put(el.getName(),el);
    }

    public static Status of(String name){
        return VALUES.get(name);
    }

    @Getter(onMethod = @__({@JsonValue}))
    private final String name;

    Status(String name){
        this.name=name;
    }
}
