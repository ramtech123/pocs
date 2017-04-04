package com.ramtech.logback.jibx.pojo;

/**
 * Created by rmogasale on 4/3/2017.
 */
public class LoggerConfig {

    private String name;

    private LogLevel level;

    private Integer resetTime;

    private LogLevel resetLevel;

    private Long timerStartTime;

    private boolean additivity;

    private AppenderRef appenderRef;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public boolean isAdditivity() {
        return additivity;
    }

    public void setAdditivity(boolean additivity) {
        this.additivity = additivity;
    }

    public AppenderRef getAppenderRef() {
        return appenderRef;
    }

    public void setAppenderRef(AppenderRef appenderRef) {
        this.appenderRef = appenderRef;
    }

    public Integer getResetTime() { return resetTime; }

    public void setResetTime(Integer resetTime) { this.resetTime = resetTime; }

    public LogLevel getResetLevel() { return resetLevel; }

    public void setResetLevel(LogLevel resetLevel) { this.resetLevel = resetLevel; }

    public Long getTimerStartTime() { return timerStartTime; }

    public void setTimerStartTime(Long timerStartTime) { this.timerStartTime = timerStartTime; }

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
