package com.bestSpringApplication.taskManager.models.study.implementations;


import com.bestSpringApplication.taskManager.handlers.parsers.xml.StudySchemaParser;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jdom2.Document;
import org.jdom2.JDOMException;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudySchemaImpl implements StudySchema {

    @JsonProperty("tasks")
    private Map<String, Task> tasksMap;
    private List<Dependency> dependencies;

    public static StudySchema parseFromXml(Document document) throws JDOMException {
        return StudySchemaParser.parseSchemaXml(document);
    }

    @Override
    public Task getRootTask() {
        return this.tasksMap.get("root");
    }

    // TODO: 2/15/2021
    @Override
    public StudySchema clone() throws CloneNotSupportedException {
        return null;
    }

}
