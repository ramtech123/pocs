package com.ramtech.lwj.pojo;

/**
 * Created by rmogasale on 4/3/2017.
 */
public class TiggerPolicy {

    private String tiggerPolicyClass;

    private String maxFileSize;

    public String getTiggerPolicyClass() {
        return tiggerPolicyClass;
    }

    public TiggerPolicy setTiggerPolicyClass(String tiggerPolicyClass) {
        this.tiggerPolicyClass = tiggerPolicyClass;
        return this;
    }

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public TiggerPolicy setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
        return this;
    }
}
