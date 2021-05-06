package com.bestSpringApplication.taskManager.utils.parsers.xml;

import com.bestSpringApplication.taskManager.models.abstracts.AbstractStudySchema;
import com.bestSpringApplication.taskManager.models.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.classes.DefaultStudySchemaImpl;
import com.bestSpringApplication.taskManager.models.classes.DependencyWithRelationType;
import com.bestSpringApplication.taskManager.models.classes.TimedTask;
import com.bestSpringApplication.taskManager.models.enums.RelationType;
import com.bestSpringApplication.taskManager.models.interfaces.Dependency;
import com.bestSpringApplication.taskManager.utils.DateHandler;
import com.bestSpringApplication.taskManager.utils.exceptions.internal.ParseException;
import com.bestSpringApplication.taskManager.utils.exceptions.internal.SchemaParseException;
import com.bestSpringApplication.taskManager.utils.exceptions.internal.TaskParseException;
import com.bestSpringApplication.taskManager.utils.parsers.SchemaParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@RequiredArgsConstructor
//NOT SEE HERE
public class XmlSchemaParser implements SchemaParser {

    @Override
    public AbstractStudySchema parse(Object parsable) throws ParseException {
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            if (parsable instanceof File){
                File file = (File) parsable;
                Document fileDocument = saxBuilder.build(file);
                return parseSchemaXml(fileDocument);
            }else if (parsable instanceof MultipartFile){
                MultipartFile multipartFile = (MultipartFile) parsable;
                Document multipartFileDocument = saxBuilder.build(multipartFile.getInputStream());
                return parseSchemaXml(multipartFileDocument);
            }else {
                throw new SchemaParseException("Parsable object is not file or multipart file");
            }
        } catch (IOException | JDOMException e) {
            throw new ParseException(String.format("Some error with getting file = %s",e.getMessage()));
        }
    }

    private AbstractStudySchema parseSchemaXml(Document mainDocument){
        Element rootElement = mainDocument.getRootElement();
        log.trace("Starting parse root element:\n{}",rootElement.getContent());
        Element fieldListElem = Optional.ofNullable(rootElement.getChild("task-field-list"))
                .orElseThrow(()-> new SchemaParseException("fieldListElem is empty!"));
        Element dependencyListElem = Optional.ofNullable(rootElement.getChild("task-dependency-list"))
                .orElseThrow(()-> new SchemaParseException("dependencyListElement is empty!"));
        Element taskElem = Optional.ofNullable(rootElement.getChild("task"))
                .orElseThrow(()-> new SchemaParseException("taskElement is empty!"));
        Map<String, String> schemaFieldsMap = xmlFieldToMap(fieldListElem, "no", "name");
        List<Dependency> dependenciesList = parseDependenciesList(dependencyListElem);
        Map<String, AbstractTask> tasksMap = parseTasksAndAddDependencies(taskElem,dependenciesList);
        addFieldsToTasks(tasksMap,schemaFieldsMap);
        log.trace("Returning study schema = {}",tasksMap.get("root"));
        return new DefaultStudySchemaImpl(tasksMap,dependenciesList,tasksMap.remove("root"));
    }

    private Map<String,AbstractTask> parseTasksAndAddDependencies(Element element, List<Dependency> dependencies){
        log.trace("Receiving element = {}",element);
        Stack<Element> tasksStack = new Stack<>();
        List<AbstractTask> taskList = new ArrayList<>();
        tasksStack.push(element);
        while (!tasksStack.empty()) {
            Element taskElemFromStack = tasksStack.pop();
            TimedTask.TimedTaskBuilder taskBuilder = TimedTask.builder();
            String taskName = getImportantElemText(taskElemFromStack, "task-name");
            String taskId = getImportantElemText(taskElemFromStack, "task-id");
            Optional<Element> taskListElem = getChildElemOpt(taskElemFromStack, "task-list");
            taskBuilder
                    .name(taskName)
                    .id(taskId)
                    .fields(parseFieldListElem(taskElemFromStack))
                    .notes(parseTaskNotes(taskElemFromStack))
                    .startDate(parseDate(taskElemFromStack,"task-start-date"))
                    .endDate(parseDate(taskElemFromStack,"task-end-date"));
            taskListElem.ifPresent(tasksOpt->{
                taskBuilder.theme(true);
                Optional<List<Element>> tasks = Optional.ofNullable(tasksOpt.getChildren("task"));
                tasks.ifPresent(tasksListOpt->
                        tasksListOpt.forEach(el->{
                            String taskListElemId = el.getChildText("task-id");
                            dependencies.add(new DependencyWithRelationType(RelationType.HIERARCHICAL,taskId,taskListElemId));
                            tasksStack.push(el);
                        })
                );
            });
            taskList.add(taskBuilder.build());
        }
        Map<String, AbstractTask> tasksMap = new HashMap<>();
        AbstractTask rootCourseTask = taskList.get(1);
        tasksMap.put("root",rootCourseTask);
        taskList.forEach(task->tasksMap.put(task.getId(),task));
        return tasksMap;
    }

    private List<Dependency> parseDependenciesList(Element dependencyListElem) {
        log.trace("Starting parse dependencies list xml element = {}",dependencyListElem);
        List<Element> DependencyElements = dependencyListElem.getChildren("task-dependency");
        return DependencyElements.stream()
                .map(DependencyChild ->{
                    String parent = DependencyChild.getChildText("task-predecessor-id");
                    String child = DependencyChild.getChildText("task-successor-id");
                    return new DependencyWithRelationType(RelationType.WEAK,parent,child);
                }).collect(toList());
    }

    private void addFieldsToTasks(Map<String, AbstractTask> tasks, Map<String, String> schemaFields) {
        tasks.values().stream()
                .filter(TimedTask.class::isInstance)
                .map(TimedTask.class::cast)
                .forEach(task -> {
                    Map<String, String> taskFields = task.getFields();
                    if (taskFields!=null){
                        for (int i=0;i<taskFields.size();i++) {
                            String i0 = String.valueOf(i);
                            String key = schemaFields.get(i0);
                            String value = taskFields.remove(i0);
                            taskFields.put(key,value);
                        }
                    }
                });
    }

    private Map<String,String> xmlFieldToMap(Element element, String key, String value){
        List<Element> fields = element.getChildren("field");
        Map<String,String> fieldsMap = new HashMap<>(fields.size());
        fields.forEach(el->fieldsMap.put(el.getChildText(key),el.getChildText(value)));
        return fieldsMap;
    }

    private Map<String, String> parseFieldListElem(Element taskElemFromStack) {
        return getChildElemOpt(taskElemFromStack, "field-list")
                .map(fieldList -> xmlFieldToMap(fieldList, "field-no", "field-value"))
                .orElseGet(HashMap::new);
    }

    private String parseTaskNotes(Element taskElemFromStack) {
        return getChildElemOpt(taskElemFromStack, "task-notes")
                .map(notes -> StringUtils.normalizeSpace(StringEscapeUtils.unescapeHtml4(notes.getValue())))
                .orElse("");
    }

    private long parseDate(Element element,String dateTag){
        String format = "dd-MM-yyyy, HH:mm:ss";
        Optional<String> dateOpt = getChildTextOpt(element, dateTag);
        return DateHandler.parseDateToLongFromFormatOrNow(dateOpt.orElse(null),format);
    }

    private Optional<String> getChildTextOpt(Element elem, String childTag) {
        return Optional.ofNullable(elem.getChildText(childTag));
    }

    private Optional<Element> getChildElemOpt(Element taskElemFromStack, String childTag) {
        return Optional.ofNullable(taskElemFromStack.getChild(childTag));
    }

    private String getImportantElemText(Element taskElemFromStack, String childTag) {
        return Optional.ofNullable(taskElemFromStack.getChildText(childTag))
                .filter(str -> str.length() != 0)
                .orElseThrow(() -> new TaskParseException(childTag+" is empty!"));
    }
}
