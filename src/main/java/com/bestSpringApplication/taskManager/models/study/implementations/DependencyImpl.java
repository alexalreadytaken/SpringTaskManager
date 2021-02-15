package com.bestSpringApplication.taskManager.models.study.implementations;

import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;

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
    public String getId0() {
        return childId;
    }

    @Override
    public String getId1() {
        return parentId;
    }
}
