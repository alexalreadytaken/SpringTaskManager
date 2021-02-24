package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.handlers.exceptions.forClient.ContentNotFoundException;
import com.bestSpringApplication.taskManager.handlers.exceptions.forClient.UserNotFoundException;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentSchemasService {

    @NonNull private final MasterSchemasService masterSchemasService;
    @NonNull private final UserService userService;
    @NonNull private final UserTaskRelationService utrService;

    //       student_id -> [ schema_key -> schema,...]
    private Map<String,Map<String, AbstractStudySchema>> studentsWithSchemas;

    @PostConstruct
    private void init(){
        studentsWithSchemas = new HashMap<>();
    }

    public void setSchemaToStudent(String studentId,String schemaKey){
        userService.validateExistsAndContainsRole(studentId,Role.STUDENT);

        Optional
                .ofNullable(studentsWithSchemas.get(studentId))
                .orElseGet(() -> studentsWithSchemas.put(studentId, new HashMap<>()));

        AbstractStudySchema masterSchema = masterSchemasService.schemaByKey(schemaKey);

        AbstractStudySchema clonedMasterSchema = SerializationUtils.clone(masterSchema);

        studentsWithSchemas.get(studentId).put(schemaKey,clonedMasterSchema);

        utrService.prepareFirstTasks(clonedMasterSchema,studentId);
    }

    // FIXME: 2/19/2021 not beautiful + concat code from utrService
    public boolean checkTaskForOpen(String taskId,String studentId,String schemaKey){
        Map<String, AbstractStudySchema> studentSchemas = Optional
                .ofNullable(studentsWithSchemas.get(studentId))
                .orElseThrow(() -> new ContentNotFoundException("Курсы не найдены"));

        AbstractStudySchema schema = Optional.ofNullable(studentSchemas.get(schemaKey))
                .orElseThrow(() -> new ContentNotFoundException("Курс не назначен"));

        Map<String, AbstractTask> tasksMap = schema.getTasksMap();
        List<Dependency> dependencies = schema.getDependencies();

        AbstractTask task = Optional.ofNullable(tasksMap.get(taskId))
                .orElseThrow(() -> new ContentNotFoundException("Задание не найдено"));


        return true;
    }

    public List<AbstractTask> studentSchemasOverview(String studentId){
        Map<String, AbstractStudySchema> concreteStudentSchemas = Optional
                .ofNullable(studentsWithSchemas.get(studentId))
                .orElseThrow(() -> new UserNotFoundException("Студент не найден"));

        return concreteStudentSchemas
                .values().stream()
                .map(AbstractStudySchema::getRootTask)
                .collect(Collectors.toList());
    }
}
