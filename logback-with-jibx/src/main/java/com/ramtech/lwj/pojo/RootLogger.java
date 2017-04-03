package com.ramtech.lwj.pojo;

/**
 * Created by rmogasale on 4/3/2017.
 */
public class RootLogger {

    private LogLevel level;

    private AppenderRef appenderRef;

    public String getName() {
        return "ROOT";
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public AppenderRef getAppenderRef() {
        return appenderRef;
    }

    public void setAppenderRef(AppenderRef appenderRef) {
        this.appenderRef = appenderRef;
    }
}
