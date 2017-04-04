package com.ramtech.logback.jaxb.pojo;

/**
 * Created by rmogasale on 4/3/2017.
 */
public enum LogLevel {

    ERROR,
    WARN,
    INFO,
    DEBUG;

    public static LogLevel fromValue(String text) {
        if(isEmpty(text)) {
            throw new IllegalArgumentException("Null or blank data not allowed");
        }
        return LogLevel.valueOf(text.toUpperCase());
    }

    private static boolean isEmpty (String text) {
        return text == null || text.trim().isEmpty();
    }

}
