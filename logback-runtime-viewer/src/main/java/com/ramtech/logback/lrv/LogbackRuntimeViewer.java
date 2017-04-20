package com.ramtech.logback.lrv;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rmogasale on 4/20/2017.
 */
public class LogbackRuntimeViewer {

    org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LogbackRuntimeViewer.class);


    public static void main(String[] args) throws JoranException, IOException {
        LogbackRuntimeViewer viewer = new LogbackRuntimeViewer();
        viewer.init();
        String loggerNameToTest = "com.ramtech.logback.lrv.test";
        viewer.runTest(loggerNameToTest);

    }

    private void runTest(String loggerNameToTest) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.getLogger(loggerNameToTest);
        testLoggingLevels(logger);
        setupChildLogger(logger, "ERROR");
        testLoggingLevels(logger);
        setupChildLogger(logger, "DEBUG");
        testLoggingLevels(logger);
        LOGGER.info("Detaching logger from all the appenders..");
        logger.detachAndStopAllAppenders();
        testLoggingLevels(logger);
        LOGGER.info("Removing logger level to let it inherit parent");
        logger.setLevel(null);
        testLoggingLevels(logger);
        LOGGER.info("Execution completed");
    }

    private static void testLoggingLevels(Logger logger) {
        logger.error("---------- START: Test Logs ----------");
        logger.debug("This is debug statement: {}", getLoggerInfo(logger));
        logger.info("This is info statement: {}", getLoggerInfo(logger));
        logger.warn("This is warn statement: {}", getLoggerInfo(logger));
        logger.error("This is error statement: {}", getLoggerInfo(logger));
        logger.error("---------- END: Test Logs ----------");
    }

    private static String getLoggerInfo(Logger logger) {
        return logger.getName() + ", " + logger.getEffectiveLevel().levelStr;
    }

    private void setupChildLogger(Logger logger, String logLevel) {
        LOGGER.info("Current level for Logger[{}] is [{}], new level is [{}]",
                logger.getName(), logger.getEffectiveLevel().levelStr, logLevel);
        logger.setLevel(Level.toLevel(logLevel));

    }

    private void init() throws JoranException, IOException {
        configureLogger();
        LOGGER.info("Logger configured successfully;");
    }

    private void configureLogger() throws JoranException, IOException {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        String logConfigPath = "/config/logback.xml";
        InputStream is = getClass().getResourceAsStream(logConfigPath);
        configurator.doConfigure(is);
    }


}
