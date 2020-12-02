package com.bestSpringApplication.taskManager.model.task.classes;

public class FieldImpl {
    private String no;
    private String name;

    public FieldImpl(String no, String name) {
        this.no = no;
        this.name = name;
    }

    public FieldImpl() {
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
