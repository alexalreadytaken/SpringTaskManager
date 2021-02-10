package com.bestSpringApplication.taskManager.models.enums;

import java.util.Arrays;

public enum Role {
    STUDENT("student"),TEACHER("teacher"),ADMIN("admin");

    private final String strValue;
    Role(String strValue){
        this.strValue=strValue;
    }
    public String getStrValue(){
        return this.strValue;
    }

    public static Role getInstance(String str) {
        return Arrays.stream(Role.values())
                .filter(el->el.getStrValue().equals(str))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
