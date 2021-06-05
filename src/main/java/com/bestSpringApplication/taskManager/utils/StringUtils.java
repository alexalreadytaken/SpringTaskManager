package com.bestSpringApplication.taskManager.utils;

import java.util.regex.Pattern;

public class StringUtils {

    public final static Pattern intPattern = Pattern.compile("\\d+");

    public static boolean isDigit(String str){
        return intPattern.matcher(str).matches();
    }

}
