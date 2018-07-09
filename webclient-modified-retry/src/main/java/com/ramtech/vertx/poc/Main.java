package com.ramtech.vertx.poc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

import io.vertx.rxjava.core.Vertx;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final Vertx vertx = Vertx.vertx();

    public static void main(String[] args) throws JoranException {
        configureLogger();
        Server.httpServer(vertx)
                .subscribe(server -> {
                            LOGGER.info("Success response!");
                            Client.init(vertx);
                            Client.triggerRequest();
                        },
                        ex -> LOGGER.error("Unable to launch server or proxy", ex));
    }

    private static void configureLogger() throws JoranException {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        configurator.doConfigure(Main.class.getResourceAsStream("/config/logback.xml"));
    }
}
