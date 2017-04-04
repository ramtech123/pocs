package com.ramtech.logback.jaxb.pojo;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by rmogasale on 4/3/2017.
 */
public class AppenderRef {

    private String ref;

    @XmlAttribute
    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
