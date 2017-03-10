package com.ramtech.custom.action;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import org.xml.sax.Attributes;

/**
 * Created by rmogasale on 3/9/2017.
 */
public class ResetLevelAttributeAction extends Action {

    boolean inError = false;
    Logger logger;

    public void begin(InterpretationContext iContext, String s, Attributes attributes) throws ActionException {
        this.inError = false;
        LoggerContext loggerContext = (LoggerContext)this.context;
        String loggerName = iContext.subst(attributes.getValue("name"));
        this.logger = loggerContext.getLogger(loggerName);
        String resetLevelStr = iContext.subst(attributes.getValue("resetLevelAfter"));
        if (resetLevelStr != null) {
            this.addInfo("Level for logger {" + loggerName + "} will be reset after {" + resetLevelStr + "} minutes");
        }
    }

    public void end(InterpretationContext iContext, String s) throws ActionException {

    }
}
