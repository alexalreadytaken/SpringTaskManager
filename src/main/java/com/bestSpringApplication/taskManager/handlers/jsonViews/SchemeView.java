package com.bestSpringApplication.taskManager.handlers.jsonViews;

public final class SchemeView {
    public interface InfoForGraph{}
    public interface FullInfo extends InfoForGraph,TaskView.InfoForGraph{}
}
