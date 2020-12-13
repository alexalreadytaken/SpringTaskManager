package com.bestSpringApplication.taskManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class TaskManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}
}
