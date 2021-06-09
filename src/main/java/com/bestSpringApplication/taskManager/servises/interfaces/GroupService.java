package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.entities.Group;
import com.bestSpringApplication.taskManager.models.entities.User;

import java.util.List;

public interface GroupService {

    void validateGroupExistsOrThrow(String groupId);

    Group getGroupById(String groupId);

    List<Group> getGroupList();

    List<User> getGroupUsers(String groupId);
}
