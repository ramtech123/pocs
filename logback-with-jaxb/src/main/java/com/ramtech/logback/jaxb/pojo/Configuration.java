package com.ramtech.logback.jaxb.pojo;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by rmogasale on 4/3/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "configuration")
public class Configuration {

    @XmlAttribute
    private boolean scan;

    @XmlAttribute
    private String scanPeriod;

    @XmlElement(name = "appender")
    private List<Appender> appenders;

    @XmlElement(name = "logger")
    private List<LoggerConfig> loggerConfigs;

    @XmlElement(name = "root")
    private RootLogger rootLogger;

    public boolean isScan() {
        return scan;
    }

    public Configuration setScan(boolean scan) {
        this.scan = scan;
        return this;
    }

    public String getScanPeriod() {
        return scanPeriod;
    }

    public Configuration setScanPeriod(String scanPeriod) {
        this.scanPeriod = scanPeriod;
        return this;
    }

    public List<Appender> getAppenders() {
        return appenders;
    }

    public Configuration setAppenders(List<Appender> appenders) {
        this.appenders = appenders;
        return this;
    }

    public List<LoggerConfig> getLoggerConfigs() {
        return loggerConfigs;
    }

    public Configuration setLoggerConfigs(List<LoggerConfig> loggerConfigs) {
        this.loggerConfigs = loggerConfigs;
        return this;
    }

    public RootLogger getRootLogger() {
        return rootLogger;
    }

    public Configuration setRootLogger(RootLogger rootLogger) {
        this.rootLogger = rootLogger;
        return this;
    }
}
