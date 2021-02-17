package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.handlers.exceptions.UserNotFoundException;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
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
    private Map<String,Map<String,StudySchema>> studentsWithSchemas;

    @PostConstruct
    private void init(){
        studentsWithSchemas = new HashMap<>();
    }

    public void setSchemaToStudent(String studentId,String schemaKey){
        if (!userService.existsUserById(studentId)) throw new UserNotFoundException("Студент не найден");

        Optional
                .ofNullable(studentsWithSchemas.get(studentId))
                .orElseGet(() -> studentsWithSchemas.put(studentId, new HashMap<>()));

        StudySchema masterSchema = masterSchemasService.schemaByKey(schemaKey);

        StudySchema clonedMasterSchema = SerializationUtils.clone(masterSchema);

        studentsWithSchemas.get(studentId).put(schemaKey, clonedMasterSchema);
    }

    public List<Task> studentSchemasOverview(String studentId){
        Map<String, StudySchema> concreteStudentSchemas = Optional
                .ofNullable(studentsWithSchemas.get(studentId))
                .orElseThrow(() -> new UserNotFoundException("Студент не найден"));

        return concreteStudentSchemas
                .values().stream()
                .map(StudySchema::getRootTask)
                .collect(Collectors.toList());
    }
}
