package com.ramtech.vertx.poc;

import java.util.Observable;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.http.HttpServerOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import rx.Single;

public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public static Single<HttpServer> httpServer(final Vertx vertx) {
        return createHttpServer(vertx, getServerOptions(8095), Server::handleRequest);
    }

    private static void handleRequest(final RoutingContext context) {
        String header = context.request().getHeader("Auth");
        LOGGER.info("Received header value: {}", header);
        if (header == null || header.equals("Fail")) {
            LOGGER.info("Responding with failure..");
            // context.response().close();
        } else {
            context.
                    response()
                    .setChunked(true)
                    .setStatusCode(200)
                    .write("This is success response! Request body is " + context.getBodyAsString())
                    .end();
        }
    }

    public static Single<HttpServer> createHttpServer(final Vertx vertx, final HttpServerOptions options, final Consumer<RoutingContext> consumer) {
        return vertx.createHttpServer(options)
                .requestHandler(request -> getRouter(vertx, consumer).accept(request))
                .rxListen()
                .doOnSuccess(server -> LOGGER.info("Server listening on {}:{}", options.getHost(), options.getPort()))
                .doOnError(e -> LOGGER.error("Server unable to listen on {}:{}", options.getHost(), options.getPort()));
    }

    public static HttpServerOptions getServerOptions(final int serverPort) {
        return new HttpServerOptions()
                .setIdleTimeout(15)
                .setHost("localhost")
                .setPort(serverPort)
                .setMaxChunkSize(10);
    }

    private static Router getRouter(final Vertx vertx, final Consumer<RoutingContext> consumer) {
        Router router = Router.router(vertx);
        router.get().handler(consumer::accept);
        return router;
    }
}
