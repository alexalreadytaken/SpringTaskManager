package com.bestSpringApplication.taskManager.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class FileDeleter {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteAsync(File file,long waitingMillisTime){
        new Thread(()->{
            try {
                Thread.sleep(waitingMillisTime);
                file.delete();
            } catch (InterruptedException e) {
                log.error("error with deleting file '{}' = {}",file.getName(),e.getMessage());
            }
        }).start();
    }
}
