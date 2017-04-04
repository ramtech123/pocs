package com.ramtech.logback.jaxb.pojo;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by rmogasale on 4/3/2017.
 */
public class RootLogger extends LoggerBase {

    @XmlTransient
    public String getName() {
        return "ROOT";
    }

}
