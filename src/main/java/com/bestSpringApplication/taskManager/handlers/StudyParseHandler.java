package com.bestSpringApplication.taskManager.handlers;

import com.bestSpringApplication.taskManager.models.study.abstracts.AbstractTask;
import com.bestSpringApplication.taskManager.models.study.classes.HierarchicalTaskImpl;
import org.jdom2.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyParseHandler {

    public static void addTaskFields(List<AbstractTask> tasks, Map<String, String> schemaFields) {
        tasks.forEach(task -> {
            try {
                HierarchicalTaskImpl hierarchicalTaskImpl = (HierarchicalTaskImpl) task;
                Map<String, String> taskFields = hierarchicalTaskImpl.getFields();
                for (int i = 0; taskFields!=null && i <  taskFields.size(); i++) {
                    String i0 = String.valueOf(i);
                    String key = schemaFields.get(i0);
                    String value = taskFields.remove(i0);
                    taskFields.put(key,value);
                }
            }catch (ClassCastException ignored){}
        });
    }

    public static Map<String,String> xmlFieldToMap(Element element, String field, String key, String value){
        List<Element> fields = element.getChildren(field);
        Map<String,String> fieldsMap = new HashMap<>();
        fields.forEach(el ->fieldsMap.put(el.getChildText(key), el.getChildText(value)));
        return fieldsMap;
    }

}


