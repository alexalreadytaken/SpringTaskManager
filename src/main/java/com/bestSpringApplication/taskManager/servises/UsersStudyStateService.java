package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.models.classes.StudySchema;
import com.bestSpringApplication.taskManager.models.classes.StudyTask;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.entities.User;
import com.bestSpringApplication.taskManager.models.entities.UserTaskState;
import com.bestSpringApplication.taskManager.models.enums.RelationType;
import com.bestSpringApplication.taskManager.models.enums.Status;
import com.bestSpringApplication.taskManager.repos.UserTaskStateRepo;
import com.bestSpringApplication.taskManager.servises.interfaces.GroupService;
import com.bestSpringApplication.taskManager.servises.interfaces.StudyStateService;
import com.bestSpringApplication.taskManager.utils.exceptions.forClient.BadRequestException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersStudyStateService implements StudyStateService {

    @NonNull private final UserTaskStateRepo utrRepo;
    @NonNull private final GroupService groupService;

    public void prepareSchema(StudySchema schema, String userId){
        log.trace("start prepare schema '{}' to student '{}'",schema.getId(),userId);
        schema.tasksStream()
                .filter(task -> !task.isTheme())
                .peek(task -> prepareTask(schema, task, userId))
                .filter(task -> firstCheckTask(schema, task))
                .peek(task->log.trace("task 'name:{},id:{}' validated successful",task.getName(),task.getId()))
                .forEach(task->openTask(schema.getId(),userId,task.getId()));
    }

    public void prepareTask(StudySchema schema, StudyTask task, String userId){
        UserTaskState userTaskState = UserTaskState.builder()
                .schemaId(schema.getId())
                .status(Status.CLOSED)
                .taskId(task.getId())
                .userId(userId)
                .percentComplete(0.0)
                .build();
        utrRepo.save(userTaskState);
    }

    public void openTask(String schemaId, String userId, String taskId){
        utrRepo.setStatusForTask(schemaId, userId, taskId,Status.IN_WORK);
    }

    public void setStatusForUserTask(String schemaId, String userId, String taskId, Status status) {
        utrRepo.setStatusForTask(schemaId, userId, taskId, status);
    }

    public void setPercentCompleteForUserTask(String schemaId, String userId, String taskId, double percent) {
        utrRepo.setPercentCompleteForTask(schemaId, userId, taskId, percent);
    }

    public void setStatusAndPercentCompleteForUserTask(String schemaId, String userId, String taskId, Status status, double percent) {
        utrRepo.setStatusAndPercentCompleteForTask(schemaId, userId, taskId, percent, status);
    }

    public boolean schemaOfUserExists(String schemaId, String userId) {
        return utrRepo.existsBySchemaIdAndUserId(schemaId, userId);
    }

    public boolean schemaOfGroupExists(String groupId, String schemaId) {
        return groupService.getGroupById(groupId)
                .getUsers()
                .stream()
                .map(User::getId)
                .map(String::valueOf)
                .anyMatch(usrId->schemaOfUserExists(schemaId,usrId));
    }

    public List<String> getCompletedTasksIdOfSchemaForUser(String schemaId, String userId) {
        List<String> ids = utrRepo.getCompletedTasksIdOfSchemaIdAndUserId(userId, schemaId);
        throwIfListEmpty(ids,"курс не назначен или нет завершенных заданий");
        return ids;
    }

    public List<String> getOpenedSchemasIdOfUser(String userId){
        List<String> openedSchemasIdOfUser = utrRepo.getOpenedSchemasIdOfUser(userId);
        throwIfListEmpty(openedSchemasIdOfUser,"человеку не назначен ни один курс");
        return openedSchemasIdOfUser;
    }

    @Override
    public List<UserTaskState> getUnconfirmedTasks() {
        List<UserTaskState> unconfirmedTasks = utrRepo.getAllByStatus(Status.UNCONFIRMED);
        throwIfListEmpty(unconfirmedTasks,"рапортов о готовности нет");
        return unconfirmedTasks;
    }

    public List<UserTaskState> getAllStateBySchemaId(String schemaId){
        List<UserTaskState> schemaRelations = utrRepo.getAllBySchemaId(schemaId);
        throwIfListEmpty(schemaRelations,"курс не назначен ни одному человеку");
        return schemaRelations;
    }

    public List<UserTaskState> getSchemaStateByUserId(String userId, String schemaId){
        List<UserTaskState> userSchemaState = utrRepo.getAllBySchemaIdAndUserId(schemaId, userId);
        throwIfListEmpty(userSchemaState,"курс не назначен данному человеку");
        return userSchemaState;
    }

    public List<UserTaskState> getAllUserStates(String userId) {
        List<UserTaskState> allUserState = utrRepo.getAllByUserId(userId);
        throwIfListEmpty(allUserState,"человеку не назначен ни один курс");
        return allUserState;
    }

    public List<UserTaskState> getAllUserStatesByStatus(String userId, Status status) {
        List<UserTaskState> allUserState = utrRepo.getAllByUserIdAndStatus(userId,status);
        throwIfListEmpty(allUserState,"у человека нет заданий со статусом = "+status.getRuValue());
        return allUserState;
    }

    @Override
    public List<UserTaskState> getAllUserStatesBySchemaAndStatus(String userId, String schemaId, Status status) {
        List<UserTaskState> allUserState = utrRepo.getAllByUserIdAndSchemaIdAndStatus(userId,schemaId,status);
        throwIfListEmpty(allUserState,"в этом курсе у человека нет заданий со статусом = "+status.getRuValue());
        return allUserState;
    }

    public List<UserTaskState> getTaskStateInSchema(String schemaId, String taskId){
        List<UserTaskState> taskOfSchemaState = utrRepo.getAllBySchemaIdAndTaskId(schemaId, taskId);
        throwIfListEmpty(taskOfSchemaState,"данное задание никому не назначено");
        return taskOfSchemaState;
    }

    public List<String> getOpenedTasksIdBySchemaOfUser(String userId, String schemaId){
        List<String> openedSchemaTasks = utrRepo.getTasksIdOfSchemaIdAndUserIdAndStatus(userId, schemaId, Status.IN_WORK);
        throwIfListEmpty(openedSchemaTasks,"у человека нет активных заданий на текущем курсе");
        return openedSchemaTasks;
    }

    public boolean taskFinished(String schemaId, String userId, String taskId) {
        return utrRepo.existsByTaskIdAndSchemaIdAndUserIdAndStatus(taskId,schemaId,userId,Status.FINISHED);
    }

    public boolean taskContainsStatus(String schemaId, String userId, String taskId, Status status) {
        return utrRepo.existsByTaskIdAndSchemaIdAndUserIdAndStatus(taskId,schemaId,userId,status);
    }

    public boolean taskInWork(String schemaId, String userId, String taskId){
        return utrRepo.existsByTaskIdAndSchemaIdAndUserIdAndStatus(taskId,schemaId,userId,Status.IN_WORK);
    }

    public boolean taskExists(String schemaId, String userId, String taskId){
        return utrRepo.existsBySchemaIdAndUserIdAndTaskId(schemaId, userId, taskId);
    }

    private boolean firstCheckTask(StudySchema schema, StudyTask task){
        List<DependencyWithRelationType> dependencies = schema.getDependenciesWithRelationType();
        Map<String, StudyTask> tasksMap = schema.getTasksMap();

        return dependencies.stream()
                .filter(dependency -> dependency.getId1().equals(task.getId()))
                .allMatch(dependency -> {
                    StudyTask taskParent = tasksMap.get(dependency.getId0());
                    boolean parentHierarchicalAndTheme = taskParent.isTheme() &&
                            dependency.getRelationType() == RelationType.HIERARCHICAL;
                    boolean parentsOfParentValid = firstCheckTask(schema, taskParent);
                    return parentHierarchicalAndTheme&&parentsOfParentValid;
                });
    }

    private void throwIfListEmpty(List<?> list,String exceptionMessage){
        if (list.isEmpty())throw new BadRequestException(exceptionMessage);
    }
}