package com.bestSpringApplication.taskManager.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Status {

    DEBT("debt","долг"),
    CLOSED("closed","закрыт"),
    IN_WORK("in_work","в работе"),
    FINISHED("finished","завершен"),
    NOT_CONFIRMED("not_confirmed","не подтвержден"),
    REOPENED("reopened","открыт повторно");

    private static final Map<String,Status> VALUES = new HashMap<>(Status.values().length);

    static {
        for(Status el: values()) {
            VALUES.put(el.getEnValue(),el);
            VALUES.put(el.getRuValue(),el);
        }
    }

    public static Optional<Status> of(String name){
        return Optional.ofNullable(VALUES.get(name));
    }

    public static List<String> ruValues(){
        return Stream.of(values())
                .map(Status::getRuValue)
                .collect(Collectors.toList());
    }

    @Getter
    private final String enValue;
    @Getter(onMethod = @__({@JsonValue}))
    private final String ruValue;

    public boolean isInstance(Status other){
        return this==other;
    }

    Status(String enValue, String ruValue){
        this.enValue = enValue;
        this.ruValue = ruValue;
    }
}
