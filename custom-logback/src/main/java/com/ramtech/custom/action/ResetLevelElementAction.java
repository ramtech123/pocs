package com.ramtech.custom.action;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import org.xml.sax.Attributes;


/**
 * Created by rmogasale on 3/9/2017.
 */
public class ResetLevelElementAction extends Action {

    boolean inError = false;

    public void begin(InterpretationContext iContext, String name, Attributes attributes) throws ActionException {
        Object o = iContext.peekObject();
        if (!(o instanceof Logger)) {
            this.inError = true;
            this.addError("For element <reset-level>, could not find a logger at the top of execution stack.");
        }
        else {
            Logger l = (Logger) o;
            String loggerName = l.getName();
            String level = l.getLevel().levelStr;
            String resetAfter = "-1";
            if (!"ERROR".equalsIgnoreCase(level)) {
                resetAfter = iContext.subst(attributes.getValue("after"));
                this.addInfo("Level for logger {"+ loggerName +"} will be reset after {" + resetAfter + "} minutes");
            }
            else {
                this.addInfo("No reset level needed for logger {"+ loggerName +"}");
            }

        }
    }

    public void end(InterpretationContext interpretationContext, String s) throws ActionException {

    }
}
