package com.bestSpringApplication.taskManager.models.enums;

public enum RelationType {
    HIERARCHICAL,WEAK;

    public boolean isInstance(RelationType other){
        return this==other;
    }
}
