package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.handlers.exceptions.UserNotFoundException;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentSchemasService {

    @NonNull private final MasterSchemasService masterSchemasService;
    @NonNull private final UserService userService;
    @NonNull private final UserTaskRelationService utrService;

    private Map<String,Map<String,StudySchema>> studentsWithSchemas;

    /*@PostConstruct FIXME: 2/17/2021
    private void init(){
        int possibleStudentsCount = (int) (userService.countByRole(Role.STUDENT)+1*1.4);
        studentsWithSchemas = new HashMap<>(possibleStudentsCount);
    }

    // TODO: 2/17/2021 check exists schema
    public void setSchemaToStudent(String studentId,String schemaKey){
        if (!userService.existsUserById(studentId)) throw new UserNotFoundException("Студент не найден");

        Map<String, StudySchema> studentSchemas = Optional
                .ofNullable(studentsWithSchemas.get(studentId))
                .orElseGet(() -> studentsWithSchemas.put(studentId, new HashMap<>()));

        StudySchema masterSchema = masterSchemasService.schemaByKey(schemaKey);
        studentSchemas.put(schemaKey,masterSchema.clone());
    }*/
}
