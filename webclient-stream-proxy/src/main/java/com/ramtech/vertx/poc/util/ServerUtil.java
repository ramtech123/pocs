package com.ramtech.vertx.poc.util;

import io.vertx.core.http.HttpServerOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Single;

import static com.ramtech.vertx.poc.constant.Constants.IDLE_TIMEOUT_SECONDS;

public class ServerUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerUtil.class);

    public static Single<HttpServer> createHttpServer(final Vertx vertx, final HttpServerOptions options, final String serverType,
                                                      final boolean handleBody, final Consumer<RoutingContext> consumer) {
        return vertx.createHttpServer(options)
                .requestHandler(request -> getRouter(vertx, handleBody, consumer, serverType).accept(request))
                .rxListen()
                .doOnSuccess(server -> LOGGER.info("{} listening on {}:{}", serverType, options.getHost(), options.getPort()))
                .doOnError(e -> LOGGER.error("{} unable to listen on {}:{}", serverType, options.getHost(), options.getPort()));
    }

    public static HttpServerOptions getServerOptions(final int serverPort) {
        return new HttpServerOptions()
                .setIdleTimeout(IDLE_TIMEOUT_SECONDS)
                .setHost("localhost")
                .setPort(serverPort);
    }

    private static Router getRouter(final Vertx vertx, final boolean handleBody, final Consumer<RoutingContext> consumer,
                                    final String serverType) {
        Router router = Router.router(vertx);
        LOGGER.info("{}: Handle body = {}", serverType, handleBody);
        if (handleBody) {
            router.post().handler(BodyHandler.create());
        }
        router.post().handler(consumer::accept);
        return router;
    }
}