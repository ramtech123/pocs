package com.ramtech.lwj.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rmogasale on 4/3/2017.
 */
public class Configuration {

    private boolean scan;

    private String scanPeriod;

    private List<Appender> appenders;

    private List<LoggerConfig> loggerConfigs;

    private RootLogger rootLogger;

    public boolean isScan() {
        return scan;
    }

    public void setScan(boolean scan) {
        this.scan = scan;
    }

    public String getScanPeriod() {
        return scanPeriod;
    }

    public void setScanPeriod(String scanPeriod) {
        this.scanPeriod = scanPeriod;
    }

    public List<Appender> getAppenders() {
        return appenders;
    }

    public void setAppenders(List<Appender> appenders) {
        this.appenders = appenders;
    }

    public List<LoggerConfig> getLoggerConfigs() {
        return loggerConfigs;
    }

    public void setLoggerConfigs(List<LoggerConfig> loggerConfigs) {
        this.loggerConfigs = loggerConfigs;
    }

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
