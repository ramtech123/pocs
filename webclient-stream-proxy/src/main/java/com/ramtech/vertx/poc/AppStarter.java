package com.ramtech.vertx.poc;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.ramtech.vertx.poc.http.Proxy;
import com.ramtech.vertx.poc.http.Server;
import io.vertx.rxjava.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ramtech.vertx.poc.http.Client.client;

/**
 * Starting point for this application.
 */
public class AppStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppStarter.class);
    private static final Vertx vertx = Vertx.vertx();

    public static void main(String[] args) throws JoranException {
//        System.setProperty(PROXY_AS_BUFFER, "true");
//        System.setProperty(NO_ASYNC_PROXY, "true");
        configureLogger();
        
        // Launch HTTP Server and proxy. Once both are ready, trigger client requests.
        Server.httpServer(vertx)
                .mergeWith(Proxy.httpProxy(vertx))
                .subscribe(server -> {
                        },
                        ex -> LOGGER.error("Unable to launch server or proxy", ex),
                        () -> {
                            LOGGER.info("Both server and proxy started successfully..! Starting the client..");
                            client(vertx).triggerRequest();
                        });
    }

    private static void configureLogger() throws JoranException {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        configurator.doConfigure(AppStarter.class.getResourceAsStream("/config/logback.xml"));
    }
}
