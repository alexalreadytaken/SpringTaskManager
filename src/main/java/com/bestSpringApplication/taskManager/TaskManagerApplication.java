package com.bestSpringApplication.taskManager;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.codec.Utf8;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class TaskManagerApplication {
	public static void main(String[] args) throws UnsupportedEncodingException {
		SpringApplication.run(TaskManagerApplication.class, args);
	}
}
