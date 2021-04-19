package com.bestSpringApplication.taskManager.utils;

import org.jdom2.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaParseHandler {

    public static Map<String,String> xmlFieldToMap(Element element, String field, String key, String value){
        List<Element> fields = element.getChildren(field);
        Map<String,String> fieldsMap = new HashMap<>();
        fields.forEach(el ->fieldsMap.put(el.getChildText(key), el.getChildText(value)));
        return fieldsMap;
    }

}


