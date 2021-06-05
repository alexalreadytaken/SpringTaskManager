package com.bestSpringApplication.taskManager.repos;

import com.bestSpringApplication.taskManager.models.entities.UserTaskState;
import com.bestSpringApplication.taskManager.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserTaskStateRepo extends JpaRepository<UserTaskState,Integer> {
    boolean existsBySchemaIdAndUserIdAndTaskId(String schemaId, String userId, String taskId);

    boolean existsByTaskIdAndSchemaIdAndUserIdAndStatus(String taskId,String schemaId,String userId,Status status);

    boolean existsBySchemaIdAndUserId(String schemaId,String userid);

    List<UserTaskState> getAllBySchemaIdAndUserId(String schemaId, String userId);

    List<UserTaskState> getAllBySchemaId(String schemaId);

    List<UserTaskState> getAllBySchemaIdAndTaskId(String schemaId, String taskId);

    List<UserTaskState> getAllByStatus(Status status);

    List<UserTaskState> getAllByUserId(String userId);

    List<UserTaskState> getAllByUserIdAndStatus(String userId,Status status);

    @Query("select distinct schemaId from UserTaskState where userId=:userId")
    List<String> getOpenedSchemasIdOfUser(@Param("userId") String userId);

    @Transactional
    @Modifying
    @Query("update UserTaskState set percentComplete=:percent,status=:status where taskId=:taskId "+
            "and schemaId=:schemaId and userId=:userId")
    void setStatusAndPercentCompleteForTask(@Param("schemaId")String schemaId,
                                            @Param("userId")String userId,
                                            @Param("taskId")String taskId,
                                            @Param("percent") double percent,
                                            @Param("status")Status status);

    @Transactional
    @Modifying
    @Query("update UserTaskState set percentComplete=:percent where taskId=:taskId "+
            "and schemaId=:schemaId and userId=:userId")
    void setPercentCompleteForTask(@Param("schemaId")String schemaId,
                                   @Param("userId")String userId,
                                   @Param("taskId")String taskId,
                                   @Param("percent") double percentComplete);

    @Transactional
    @Modifying
    @Query("update UserTaskState set status=:status where taskId=:taskId "+
            "and schemaId=:schemaId and userId=:userId")
    void setStatusForTask(@Param("schemaId")String schemaId,
                          @Param("userId")String userId,
                          @Param("taskId")String taskId,
                          @Param("status")Status status);

    @Query("select taskId from UserTaskState where userId=:userId and schemaId=:schemaId and status=:status")
    List<String> getTasksIdOfSchemaIdAndUserIdAndStatus(@Param("userId") String userId,
                                                        @Param("schemaId") String schemaId,
                                                        @Param("status") Status status);

    @Query("select taskId from UserTaskState where userId=:userId and schemaId=:schemaId " +
            "and status='finished' and percentComplete=1")
    List<String> getCompletedTasksIdOfSchemaIdAndUserId(@Param("userId") String userId,
                                                        @Param("schemaId") String schemaId);
}
