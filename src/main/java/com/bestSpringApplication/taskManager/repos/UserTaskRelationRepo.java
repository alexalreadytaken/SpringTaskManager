package com.bestSpringApplication.taskManager.repos;

import com.bestSpringApplication.taskManager.models.classes.UserTaskRelation;
import com.bestSpringApplication.taskManager.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserTaskRelationRepo extends JpaRepository<UserTaskRelation,Integer> {
    boolean existsBySchemaIdAndUserIdAndTaskId(String schemaId, String userId, String taskId);

    boolean existsByTaskIdAndSchemaIdAndUserIdAndStatus(String taskId,String schemaId,String userId,Status status);

    List<UserTaskRelation> getAllBySchemaIdAndUserId(String schemaId, String userId);

    List<UserTaskRelation> getAllBySchemaId(String schemaId);

    List<UserTaskRelation> getAllBySchemaIdAndTaskId(String schemaId,String taskId);

    // FIXME: 4/17/21 params like update ?
    @Query(value = "select distinct schemaId from user_task_relation where userId=userId")
    List<String> getOpenedSchemasIdOfUser(String userId);

    @Transactional
    @Modifying
    @Query(value = "update user_task_relation set status=:status where taskId=:taskId "+
            "and schemaId=:schemaId and userId=:userId")
    void setStatusForTask(@Param("schemaId")String schemaId,
                          @Param("userId")String userId,
                          @Param("taskId")String taskId,
                          @Param("status")Status status);

    String query_tasksBySchemaIdAndUserId = "select taskId from user_task_relation where userId=userId and schemaId=schemaId ";

    @Query(value = query_tasksBySchemaIdAndUserId+"and status='in_work'")
    List<String> getOpenedTasksIdOfSchemaIdAndUserId(String userId, String schemaId);

    @Query(value = query_tasksBySchemaIdAndUserId+"and status='finished' and grade>=3")
    List<String> getCompletedTasksIdOfSchemaIdAndUserId(String userId, String schemaId);
}
