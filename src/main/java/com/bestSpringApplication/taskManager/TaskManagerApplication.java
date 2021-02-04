package com.bestSpringApplication.taskManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class TaskManagerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(TaskManagerApplication.class, args);
    }
}
