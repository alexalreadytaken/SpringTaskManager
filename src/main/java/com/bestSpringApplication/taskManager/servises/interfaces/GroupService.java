package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.entities.Group;

public interface GroupService {

    void validateGroupExistsOrThrow(String groupId);

    Group getGroupById(String groupId);
}
