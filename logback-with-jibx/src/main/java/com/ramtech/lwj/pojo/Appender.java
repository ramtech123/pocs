package com.ramtech.lwj.pojo;

/**
 * Created by rmogasale on 4/3/2017.
 */
public class Appender {

    private String name;

    private String appenderClass;

    private boolean append;

    private String file;

    private String encodePattern;

    private RollingPolicy rollingPolicy;

    private TiggerPolicy tiggerPolicy;

    private AppenderRef appenderRef;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppenderClass() {
        return appenderClass;
    }

    public void setAppenderClass(String appenderClass) {
        this.appenderClass = appenderClass;
    }

    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getEncodePattern() {
        return encodePattern;
    }

    public void setEncodePattern(String encodePattern) {
        this.encodePattern = encodePattern;
    }

    public RollingPolicy getRollingPolicy() {
        return rollingPolicy;
    }

    public void setRollingPolicy(RollingPolicy rollingPolicy) {
        this.rollingPolicy = rollingPolicy;
    }

    public TiggerPolicy getTiggerPolicy() {
        return tiggerPolicy;
    }

    public void setTiggerPolicy(TiggerPolicy tiggerPolicy) {
        this.tiggerPolicy = tiggerPolicy;
    }

    public AppenderRef getAppenderRef() {
        return appenderRef;
    }

    public void setAppenderRef(AppenderRef appenderRef) {
        this.appenderRef = appenderRef;
    }
}
