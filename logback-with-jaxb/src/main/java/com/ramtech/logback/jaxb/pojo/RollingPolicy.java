package com.ramtech.logback.jaxb.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rmogasale on 4/3/2017.
 */
@XmlRootElement(name = "rollingPolicy")
public class RollingPolicy {

    private String rollingPolicyClass;

    private Integer maxIndex;

    private String fileNamePattern;

    @XmlAttribute(name = "class")
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

    @XmlElement(name = "FileNamePattern")
    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public RollingPolicy setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
        return this;
    }
}
