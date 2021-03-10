package com.bestSpringApplication.taskManager.servises;

import com.bestSpringApplication.taskManager.handlers.exceptions.forClient.*;
import com.bestSpringApplication.taskManager.models.enums.Role;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
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

    // TODO: 3/2/2021
    public boolean canStartTask(String schemaKey, String studentId, String taskId){
        if (utrService.existsBySchemaIdAndUserIdAndTaskId(schemaKey,taskId,studentId)){
            return false;
        }else {
            AbstractStudySchema schema = getStudentSchemaOrThrow(studentId, schemaKey);

            List<Dependency> dependencies = schema.getDependencies();

            AbstractTask task = specificTaskOfStudentSchema(schemaKey, studentId, taskId);
            //todo todo todo todo  todo todo todo todo
            return false;
        }
    }

    public void forceStartTask(String schemaKey, String studentId, String taskId){
        startTask(schemaKey, studentId, taskId);
    }

    public void startTaskWithValidation(String schemaKey, String studentId, String taskId){
        if (canStartTask(schemaKey, studentId, taskId)){
            startTask(schemaKey, studentId, taskId);
        }else {
            throw new TaskClosedException("Задание невозможно начать (Перепеши текст ошибки)");
        }
    }

    public AbstractTask specificTaskOfStudentSchema(String schemaKey, String studentId, String taskId){
        AbstractStudySchema schema = getStudentSchemaOrThrow(studentId, schemaKey);
        return Optional
                .ofNullable(schema.getTasksMap().get(taskId))
                .orElseThrow(() -> new ContentNotFoundException("Задание не найдено"));
    }

    public List<AbstractTask> allOpenedStudentTasks(String studentId){
        Collection<AbstractStudySchema> studentSchemas = getStudentSchemasOrThrow(studentId).values();
        List<AbstractTask> tasks = new ArrayList<>();
        studentSchemas.forEach(schema->
                schema.getTasksMap()
                        .values()
                        .stream()
                        .filter(AbstractTask::isOpened)
                        .forEach(tasks::add));
        return tasks;
    }

    public List<AbstractTask> openedStudentTasks(String studentId,String schemaKey){
        return Optional.ofNullable(studentsWithSchemas.get(studentId))
                .map(schemasMap->Optional.ofNullable(schemasMap.get(schemaKey))
                        .map(schema->
                                schema.getTasksMap()
                                        .values().stream()
                                        .filter(AbstractTask::isOpened)
                                        .collect(Collectors.toList()))
                        .orElseThrow(()->new ContentNotFoundException("Данный курс не назначен")))
                .orElseThrow(()->new UserNotFoundException("Студент не найден"));
    }

    public List<AbstractTask> studentSchemasOverview(String studentId){
        return getStudentSchemasOrThrow(studentId)
                .values().stream()
                .map(AbstractStudySchema::getRootTask)
                .collect(Collectors.toList());
    }

    // FIXME: 2/19/2021
    /*public boolean checkTaskForOpen(String taskId,String studentId,String schemaKey){
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
    }*/

    public AbstractStudySchema getStudentSchemaOrThrow(String studentId,String schemaKey){
        return Optional
                .ofNullable(getStudentSchemasOrThrow(studentId).get(schemaKey))
                .orElseThrow(()->new ContentNotFoundException("Курс не назначен или не существует"));
    }

    public Map<String, AbstractStudySchema> getStudentSchemasOrThrow(String studentId) {
        return Optional
                .ofNullable(studentsWithSchemas.get(studentId))
                .orElseThrow(() -> new UserNotFoundException("Студент не найден"));
    }

    private void startTask(String schemaKey, String studentId, String taskId) {
        AbstractStudySchema schema = getStudentSchemaOrThrow(studentId, schemaKey);
        AbstractTask task = specificTaskOfStudentSchema(schemaKey, studentId, taskId);
        utrService.prepareTask(schema,task,studentId);
    }
}



