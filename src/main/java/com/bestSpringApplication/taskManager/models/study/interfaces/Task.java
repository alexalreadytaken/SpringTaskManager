package com.bestSpringApplication.taskManager.models.study.interfaces;

import java.io.Serializable;
import java.util.List;

public interface Task extends Serializable {
    String getId();
    String getName();
    String getParentId();
    List<String> getChildrenId();
    boolean isTheme();

}
