package com.ramtech.logback.jaxb.adapter;

import com.ramtech.logback.jaxb.pojo.LogLevel;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by rmogasale on 4/4/2017.
 */
public class LogLevelAdapter extends XmlAdapter<String, LogLevel> {

    @Override
    public LogLevel unmarshal(String text) throws Exception {
        System.out.println("Unmarshal: [" + text + "]" );
        if(isEmpty(text)) {
            System.out.println("I will throw exception now");
            throw new IllegalArgumentException("Null or blank data not allowed");
        }
        return LogLevel.valueOf(text.toUpperCase());
    }

    @Override
    public String marshal(LogLevel value) throws Exception {
        //System.out.println("Marshal: [" + value + "]" );
        if (value == null) {
            return null;
        }
        return value.name();
    }

    private static boolean isEmpty (String text) {
        return text == null || text.trim().isEmpty();
    }
}
