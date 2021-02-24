package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.handlers.DateHandler;
import com.bestSpringApplication.taskManager.handlers.StudyParseHandler;
import com.bestSpringApplication.taskManager.handlers.exceptions.internal.ParseException;
import com.bestSpringApplication.taskManager.handlers.exceptions.internal.TaskParseException;
import com.bestSpringApplication.taskManager.handlers.parsers.TaskParser;
import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.study.classes.TaskImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jdom2.Element;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class XmlTaskParser implements TaskParser {

    @Override
    public List<AbstractTask> parse(Object parsable) throws ParseException {
        return parseFromXml((Element) parsable);
    }

    public List<AbstractTask> parseFromXml(Element element){
        log.trace("Receiving element = {}",element);
        Stack<Element> tasksStack = new Stack<>();
        List<AbstractTask> taskList = new ArrayList<>();
        tasksStack.push(element);

        while (!tasksStack.empty()) {
            Element taskElemFromStack = tasksStack.pop();

            TaskImpl.TaskImplBuilder taskBuilder = TaskImpl.builder();

            String taskName = Optional.ofNullable(taskElemFromStack.getChildText("task-name"))
                    .orElseThrow(()-> new TaskParseException("AbstractTask id is empty!"));
            String taskId = Optional.ofNullable(taskElemFromStack.getChildText("task-id"))
                    .orElseThrow(()->new TaskParseException("AbstractTask name is empty!"));

            Optional<Element> fieldListElem = Optional.ofNullable(taskElemFromStack.getChild("field-list"));
            Optional<Element> taskListElem = Optional.ofNullable(taskElemFromStack.getChild("task-list"));
            Optional<Element> taskNotesElem = Optional.ofNullable(taskElemFromStack.getChild("task-notes"));
            Optional<String> parentId = Optional.ofNullable(taskElemFromStack.getAttributeValue("parent-id"));

            String startDate = taskElemFromStack.getChildText("task-start-date");
            String endDate = taskElemFromStack.getChildText("task-end-date");


            List<String> childrenId = new ArrayList<>();

            fieldListElem.ifPresent(fieldList ->
                    taskBuilder.fields(StudyParseHandler.fieldToMap(fieldList, "field","field-no", "field-value"))
            );
            taskNotesElem.ifPresent(notes ->
                    taskBuilder.notes(StringUtils.normalizeSpace(StringEscapeUtils.unescapeHtml4(notes.getValue())))
            );
            taskListElem.ifPresent(tasksOpt->{
                taskBuilder.theme(true);
                Optional<List<Element>> tasks = Optional.ofNullable(tasksOpt.getChildren("task"));
                tasks.ifPresent(tasksListOpt->
                        tasksListOpt.forEach(el->{
                            String taskElemId = el.getChildText("task-id");
                            childrenId.add(taskElemId);
                            el.setAttribute("parent-id",taskId);
                            tasksStack.push(el);
                        })
                );
            });
            String optimizedName = StringUtils.normalizeSpace(taskName).replaceAll(" ", "_");

            taskBuilder
                    .name(optimizedName)
                    .id(taskId)
                    .parentId(parentId.orElse(null))
                    .childrenId(childrenId)
                    .startDate(DateHandler.parseDateFromFormat(startDate,"dd-MM-yyyy, HH:mm:ss"))
                    .endDate(DateHandler.parseDateFromFormat(endDate,"dd-MM-yyyy, HH:mm:ss"));

            taskList.add(taskBuilder.build());
        }
        AbstractTask removed = taskList.remove(0);
        log.trace("Removing unused zero task = {}",removed);
        // TODO: 2/20/2021 somehow logging returned task list
        return taskList;
    }

}
