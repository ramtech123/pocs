package com.ramtech.logback.jaxb.pojo;

import javax.xml.bind.annotation.*;

/**
 * Created by rmogasale on 4/3/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "triggeringPolicy")
public class TiggerPolicy {

    @XmlAttribute(name = "class")
    private String tiggerPolicyClass;

    @XmlElement(name = "MaxFileSize")
    private String maxFileSize;

    public String getTiggerPolicyClass() {
        return tiggerPolicyClass;
    }

    public TiggerPolicy setTiggerPolicyClass(String tiggerPolicyClass) {
        this.tiggerPolicyClass = tiggerPolicyClass;
        return this;
    }

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public TiggerPolicy setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
        return this;
    }
}
