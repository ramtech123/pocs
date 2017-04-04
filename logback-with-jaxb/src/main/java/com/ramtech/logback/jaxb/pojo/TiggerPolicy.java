package com.ramtech.logback.jaxb.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rmogasale on 4/3/2017.
 */
@XmlRootElement(name = "triggeringPolicy")
public class TiggerPolicy {

    private String tiggerPolicyClass;

    private String maxFileSize;

    @XmlAttribute(name = "class")
    public String getTiggerPolicyClass() {
        return tiggerPolicyClass;
    }

    public TiggerPolicy setTiggerPolicyClass(String tiggerPolicyClass) {
        this.tiggerPolicyClass = tiggerPolicyClass;
        return this;
    }

    @XmlElement(name = "MaxFileSize")
    public String getMaxFileSize() {
        return maxFileSize;
    }

    public TiggerPolicy setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
        return this;
    }
}
