package com.ramtech.custom.configurator;

import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.ElementSelector;
import ch.qos.logback.core.joran.spi.RuleStore;
import com.ramtech.custom.action.ResetLevelAttributeAction;
import com.ramtech.custom.action.ResetLevelElementAction;

/**
 * Created by rmogasale on 3/9/2017.
 */
public class CustomJoranConfigurator extends JoranConfigurator {

    @Override
    public void addInstanceRules(RuleStore rs) {
        super.addInstanceRules(rs);
        rs.addRule(new ElementSelector("configuration/logger/reset-level"), new ResetLevelElementAction());
        rs.addRule(new ElementSelector("configuration/logger"), new ResetLevelAttributeAction());
    }
}
