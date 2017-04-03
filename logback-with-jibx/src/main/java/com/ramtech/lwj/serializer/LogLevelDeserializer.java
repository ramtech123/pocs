package com.ramtech.lwj.serializer;

import com.ramtech.lwj.pojo.LogLevel;

/**
 * Created by rmogasale on 4/3/2017.
 */
public class LogLevelDeserializer {

    public static LogLevel deserializeLevel(String text) {
        if(isEmpty(text)) {
            throw new IllegalArgumentException("Null or blank data not allowed");
        }
        return LogLevel.valueOf(text.toUpperCase());
    }

    private static boolean isEmpty (String text) {
        return text == null || text.trim().isEmpty();
    }
}
