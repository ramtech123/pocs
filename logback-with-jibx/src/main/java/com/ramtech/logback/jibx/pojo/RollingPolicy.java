package com.ramtech.logback.jibx.pojo;

/**
 * Created by rmogasale on 4/3/2017.
 */
public class RollingPolicy {

    private String rollingPolicyClass;

    private Integer maxIndex;

    private String fileNamePattern;

    public String getRollingPolicyClass() {
        return rollingPolicyClass;
    }

    public RollingPolicy setRollingPolicyClass(String rollingPolicyClass) {
        this.rollingPolicyClass = rollingPolicyClass;
        return this;
    }

    public Integer getMaxIndex() {
        return maxIndex;
    }

    public RollingPolicy setMaxIndex(Integer maxIndex) {
        this.maxIndex = maxIndex;
        return this;
    }

    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public RollingPolicy setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
        return this;
    }
}
