package com.ramtech;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.ramtech.custom.configurator.CustomJoranConfigurator;
import com.ramtech.verify.LogHandler;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Created by rmogasale on 3/9/2017.
 */
public class LogAppLoader {

    static Logger LOGGER;
    /**
     * Load the logger configuration with custom rules.
     * @return
     */
    private LogAppLoader loadLogger() throws JoranException {
        System.setProperty("logback.debug", "true");
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new CustomJoranConfigurator();
        configurator.setContext(context);
        URL logConfigFile = LogAppLoader.class.getClassLoader().getResource("config/logback.xml");
        configurator.doConfigure(logConfigFile);
        LOGGER = context.getLogger(this.getClass());
        return this;
    }

    /**
     * Verify custom changes.
     */
    private void verifyCustomBehavior() {
        LogHandler lh = new LogHandler();
        lh.handleLogging();
    }

    /**
     * Everything starts here.
     * @param args
     */
    public static void main(String[] args) throws JoranException {
        LogAppLoader appLoader = init();
        appLoader.loadLogger().verifyCustomBehavior();
        LOGGER.error("-------------------------------");
    }

    /**
     * Just a constructor call here. Open for future complexities.
     * @return
     */
    private static LogAppLoader init() {
        return new LogAppLoader();
    }
}
