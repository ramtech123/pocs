package com.ramtech.logback.jaxb.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by rmogasale on 4/3/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Appender {

    @XmlAttribute
    private String name;

    @XmlAttribute(name = "class")
    private String appenderClass;

    @XmlElement
    private boolean append;

    @XmlElement (name = "File")
    private String file;

    private Encoder encoder;

    private RollingPolicy rollingPolicy;

    private TiggerPolicy triggeringPolicy;

    private AppenderRef appenderRef;


    public String getName() {
        return name;
    }

    public Appender setName(String name) {
        this.name = name;
        return this;
    }

    public String getAppenderClass() {
        return appenderClass;
    }

    public Appender setAppenderClass(String appenderClass) {
        this.appenderClass = appenderClass;
        return this;
    }

    public boolean isAppend() {
        return append;
    }

    public Appender setAppend(boolean append) {
        this.append = append;
        return this;
    }

    public String getFile() {
        return file;
    }

    public Appender setFile(String file) {
        this.file = file;
        return this;
    }

    public Encoder getEncoder() {
        return encoder;
    }

    public Appender setEncoder(Encoder encoder) {
        this.encoder = encoder;
        return this;
    }

    public RollingPolicy getRollingPolicy() {
        return rollingPolicy;
    }

    public Appender setRollingPolicy(RollingPolicy rollingPolicy) {
        this.rollingPolicy = rollingPolicy;
        return this;
    }

    public TiggerPolicy getTriggeringPolicy() {
        return triggeringPolicy;
    }

    public Appender setTriggeringPolicy(TiggerPolicy triggeringPolicy) {
        this.triggeringPolicy = triggeringPolicy;
        return this;
    }

    public AppenderRef getAppenderRef() {
        return appenderRef;
    }

    public Appender setAppenderRef(AppenderRef appenderRef) {
        this.appenderRef = appenderRef;
        return this;
    }
}
