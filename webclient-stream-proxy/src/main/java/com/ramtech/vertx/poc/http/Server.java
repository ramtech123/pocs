package com.ramtech.vertx.poc.http;

import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Single;

import static com.ramtech.vertx.poc.constant.Constants.SERVER_PORT;
import static com.ramtech.vertx.poc.util.ServerUtil.createHttpServer;
import static com.ramtech.vertx.poc.util.ServerUtil.getServerOptions;

/**
 * Server - listens for HTTP GET requests on port 8096 and returns a success response. 
 */
public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    
    public static Single<HttpServer> httpServer(final Vertx vertx) {
        return createHttpServer(vertx, getServerOptions(SERVER_PORT) ,"'Server'", true, Server::handleRequest);
    }
    
    private static void handleRequest(final RoutingContext context) {
        LOGGER.info("Responding to request via path {}, body size = {}", context.request().path(), context.getBody().length());
        context.response()
                .setChunked(true)
                .setStatusCode(200)
                .write("This is success response! Request body is " + context.getBodyAsString())
                .end();
    }
}
