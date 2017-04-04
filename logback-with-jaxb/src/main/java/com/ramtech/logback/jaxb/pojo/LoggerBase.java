package com.ramtech.logback.jaxb.pojo;

import com.ramtech.logback.jaxb.adapter.LogLevelAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by rmogasale on 4/4/2017.
 */
public class LoggerBase {

    private LogLevel level;

    private Integer resetTime;

    private LogLevel resetLevel;

    private Long timerStartTime;

    private AppenderRef appenderRef;

    @XmlJavaTypeAdapter(LogLevelAdapter.class)
    @XmlAttribute
    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    @XmlAttribute
    public Integer getResetTime() {
        return resetTime;
    }

    public void setResetTime(Integer resetTime) {
        this.resetTime = resetTime;
    }

    @XmlJavaTypeAdapter(LogLevelAdapter.class)
    @XmlAttribute
    public LogLevel getResetLevel() {
        return resetLevel;
    }

    public void setResetLevel(LogLevel resetLevel) {
        this.resetLevel = resetLevel;
    }

    @XmlAttribute
    public Long getTimerStartTime() {
        return timerStartTime;
    }

    public void setTimerStartTime(Long timerStartTime) {
        this.timerStartTime = timerStartTime;
    }

    @XmlElement(name = "appender-ref")
    public AppenderRef getAppenderRef() {
        return appenderRef;
    }

    public void setAppenderRef(AppenderRef appenderRef) {
        this.appenderRef = appenderRef;
    }

}
