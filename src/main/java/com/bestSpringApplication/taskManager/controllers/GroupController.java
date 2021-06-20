package com.bestSpringApplication.taskManager.controllers;


import com.bestSpringApplication.taskManager.models.entities.Group;
import com.bestSpringApplication.taskManager.models.entities.User;
import com.bestSpringApplication.taskManager.servises.interfaces.GroupService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class GroupController {

    private static final String GROUP_LIST =  "/groups";
    private static final String GROUP_BY_ID = "/group/{groupId}";
    private static final String GROUP_USERS = "/group/{groupId}/users";

    @NonNull private final GroupService groupService;

    @GetMapping(GROUP_LIST)
    public List<Group> groupList(){
        return groupService.getGroupList();
    }

    @GetMapping(GROUP_BY_ID)
    public Group groupById(@PathVariable String groupId){
        log.trace("request for group info by id '{}'",groupId);
        groupService.validateGroupExistsOrThrow(groupId);
        return groupService.getGroupById(groupId);
    }

    @GetMapping(GROUP_USERS)
    public List<User> groupUsers(@PathVariable String groupId){
        log.trace("request for group users by id '{}'",groupId);
        groupService.validateGroupExistsOrThrow(groupId);
        return groupService.getGroupUsers(groupId);
    }
}
