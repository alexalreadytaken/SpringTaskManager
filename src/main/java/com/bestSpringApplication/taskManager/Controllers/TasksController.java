package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.exceptions.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.models.xmlTask.implementations.MainXml;
import com.bestSpringApplication.taskManager.parsers.xml.MainXmlParserImpl;
import org.jdom2.JDOMException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
public class TasksController {

    private static final Set<String> confirmedFileTypes =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                            "xml","mrp")));


    @PostMapping("/admin/addTasks")
    public MainXml newTasks(@RequestParam("file") MultipartFile file) throws IOException, JDOMException, NoSuchFieldException, IllegalAccessException {
        if (confirmedFileTypes.contains(file.getOriginalFilename().split("\\.")[1])){
            MainXml mainXml = MainXmlParserImpl.mainParser(file.getInputStream());
            return mainXml;
        }else {
            throw new IllegalFileFormatException("файл с таким расширением не поддерживается");
        }
    }
}
