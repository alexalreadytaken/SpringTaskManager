package com.bestSpringApplication.taskManager.models.study.implementations;

import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.fasterxml.jackson.annotation.JsonView;

public class DependencyImpl implements Dependency{

    @JsonView(Object.class)
    private String parentId;
    @JsonView(Object.class)
    private String childId;

    public DependencyImpl() { }

    public DependencyImpl(String parentId, String childId) {
        this.parentId = parentId;
        this.childId = childId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    @Override
    public String getChildId() {
        return childId;
    }

    @Override
    public String getParentId() {
        return parentId;
    }
}
