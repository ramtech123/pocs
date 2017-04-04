package com.ramtech.logback.jaxb.pojo;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by rmogasale on 4/3/2017.
 */
public class LoggerConfig extends LoggerBase {

    private String name;

    private boolean additivity;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public boolean isAdditivity() {
        return additivity;
    }

    public void setAdditivity(boolean additivity) {
        this.additivity = additivity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoggerConfig that = (LoggerConfig) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
