package com.bestSpringApplication.taskManager;

import com.bestSpringApplication.taskManager.parsers.xml.MainXmlParserImpl;
import org.apache.commons.text.StringEscapeUtils;
import org.jdom2.JDOMException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.codec.Utf8;

import java.io.*;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class TaskManagerApplication {
	public static void main(String[] args) throws IOException, JDOMException {
		SpringApplication.run(TaskManagerApplication.class, args);
//		File file = new File("C:\\Users\\rasbw\\IdeaProjects\\SpringTaskManager\\src\\main\\resources\\static\\text.xml");
//		FileInputStream fileInputStream = new FileInputStream(file);
//		MainXmlParserImpl.mainParser(fileInputStream);
	}
}
