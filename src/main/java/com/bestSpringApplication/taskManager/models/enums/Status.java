package com.bestSpringApplication.taskManager.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum Status {

    DEBT("debt"),
    CLOSED("closed"),
    IN_WORK("in_work"),
    FINISHED("finished"),
    NOT_CONFIRMED("not_confirmed"),
    REOPENED("reopened");

    private static final Map<String,Status> VALUES = new HashMap<>(Status.values().length);

    static {
        for(Status el: values()) VALUES.put(el.getName(),el);
    }

    public static Status of(String name){
        return VALUES.get(name);
    }

    @Getter(onMethod = @__({@JsonValue}))
    private final String name;

    public boolean isInstance(Status other){
        return this==other;
    }

    Status(String name){
        this.name=name;
    }
}
