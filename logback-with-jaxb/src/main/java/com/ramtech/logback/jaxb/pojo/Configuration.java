package com.ramtech.logback.jaxb.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rmogasale on 4/3/2017.
 */
@XmlRootElement(name = "configuration")
public class Configuration {

    private boolean scan;

    private String scanPeriod;

    private List<Appender> appenders;

    private List<LoggerConfig> loggerConfigs;

    private RootLogger rootLogger;

    @XmlAttribute
    public boolean isScan() {
        return scan;
    }

    public void setScan(boolean scan) {
        this.scan = scan;
    }

    @XmlAttribute
    public String getScanPeriod() {
        return scanPeriod;
    }

    public void setScanPeriod(String scanPeriod) {
        this.scanPeriod = scanPeriod;
    }

    @XmlElement(name = "appender")
    public List<Appender> getAppenders() {
        return appenders;
    }

    public void setAppenders(List<Appender> appenders) {
        this.appenders = appenders;
    }

    @XmlElement(name = "logger")
    public List<LoggerConfig> getLoggerConfigs() {
        return loggerConfigs;
    }

    public void setLoggerConfigs(List<LoggerConfig> loggerConfigs) {
        this.loggerConfigs = loggerConfigs;
    }

    @XmlElement(name = "root")
    public RootLogger getRootLogger() {
        return rootLogger;
    }

    public void setRootLogger(RootLogger rootLogger) {
        this.rootLogger = rootLogger;
    }

    public static List<Appender> appenderListFactory() {
        return new ArrayList<Appender>();
    }

    public static List<LoggerConfig> loggerListFactory() {
        return new ArrayList<LoggerConfig>();
    }
}
