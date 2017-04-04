package com.ramtech.logback.jaxb.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by rmogasale on 4/3/2017.
 */
public class Appender {

    private String name;

    private String appenderClass;

    private boolean append;

    private String file;

    private Encoder encoder;

    private RollingPolicy rollingPolicy;

    private TiggerPolicy triggeringPolicy;

    private AppenderRef appenderRef;


    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "class")
    public String getAppenderClass() {
        return appenderClass;
    }

    public void setAppenderClass(String appenderClass) {
        this.appenderClass = appenderClass;
    }

    @XmlElement
    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    @XmlElement
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Encoder getEncoder() {
        return encoder;
    }

    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    public RollingPolicy getRollingPolicy() {
        return rollingPolicy;
    }

    public void setRollingPolicy(RollingPolicy rollingPolicy) {
        this.rollingPolicy = rollingPolicy;
    }

    public TiggerPolicy getTriggeringPolicy() {
        return triggeringPolicy;
    }

    public void setTriggeringPolicy(TiggerPolicy triggeringPolicy) {
        this.triggeringPolicy = triggeringPolicy;
    }

    public AppenderRef getAppenderRef() {
        return appenderRef;
    }

    public void setAppenderRef(AppenderRef appenderRef) {
        this.appenderRef = appenderRef;
    }
}
