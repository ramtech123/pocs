package com.ramtech.logback.jaxb.pojo;

import javax.xml.bind.annotation.*;

/**
 * Created by rmogasale on 4/3/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rollingPolicy")
public class RollingPolicy {

    @XmlAttribute(name = "class")
    private String rollingPolicyClass;

    private Integer maxIndex;

    @XmlElement(name = "FileNamePattern")
    private String fileNamePattern;

    @XmlElement(name = "timeBasedFileNamingAndTriggeringPolicy")
    private TiggerPolicy triggeringPolicy;

    public TiggerPolicy getTriggeringPolicy() {
        return triggeringPolicy;
    }

    public RollingPolicy setTriggerPolicy(TiggerPolicy triggeringPolicy) {
        this.triggeringPolicy = triggeringPolicy;
        return this;
    }

    public String getRollingPolicyClass() {
        return rollingPolicyClass;
    }

    public RollingPolicy setRollingPolicyClass(String rollingPolicyClass) {
        this.rollingPolicyClass = rollingPolicyClass;
        return this;
    }

    public Integer getMaxIndex() {
        return maxIndex;
    }

    public RollingPolicy setMaxIndex(Integer maxIndex) {
        this.maxIndex = maxIndex;
        return this;
    }

    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public RollingPolicy setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
        return this;
    }
}
