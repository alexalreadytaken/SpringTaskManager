package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.entities.Group;
import com.bestSpringApplication.taskManager.repos.GroupRepo;
import com.bestSpringApplication.taskManager.servises.interfaces.GroupService;
import com.bestSpringApplication.taskManager.utils.StringUtils;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.BadRequestException;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.ContentNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {

    @NonNull private final GroupRepo groupRepo;

    @Override
    public void validateGroupExistsOrThrow(String groupId) {
        long id = parseLongIdOrThrow(groupId);
        if (!groupRepo.existsById(id))throw new ContentNotFoundException("группа не найдена");
    }

    @Override
    public Group getGroupById(String groupId) {
        return groupRepo.getById(parseLongIdOrThrow(groupId));
    }

    private long parseLongIdOrThrow(String groupId) {
        if (StringUtils.isDigit(groupId)){
            return Long.parseLong(groupId);
        }else {
            throw new BadRequestException("некоректный идентификатор группы");
        }
    }

}
