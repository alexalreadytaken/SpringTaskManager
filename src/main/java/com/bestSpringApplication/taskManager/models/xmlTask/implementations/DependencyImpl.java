package com.bestSpringApplication.taskManager.models.xmlTask.implementations;

import com.bestSpringApplication.taskManager.models.xmlTask.interfaces.Dependency;

public class DependencyImpl implements Dependency{

    private String parentId;
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
