package com.ramtech;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.vertx.core.http.HttpServerOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertxbeans.rxjava.ContextRunner;

import rx.Observable;

@Component
public class RouterTestServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterTestServer.class);

    @Autowired
    private Vertx vertx;
    @Autowired
    private ContextRunner contextRunner;
    @Value("${server.port:8443}")
    private int serverPort;
    @Value("${use.sub.routers:true}")
    private boolean useSubRouters;

    @PostConstruct
    public void init() {
        LOGGER.info("Starting the app server");
        try {
            contextRunner.executeBlocking(1,
                    () -> createRemoteAccessServer()
                            .doOnError(e -> LOGGER.error("Error while launching HTTP Server.", e)),
                    1, TimeUnit.MINUTES);
        } catch (final InterruptedException | ExecutionException | TimeoutException e) {
            LOGGER.error("Error while starting up the application.", e);
        }
        // This is a no-go zone!
        vertx.exceptionHandler(ex -> LOGGER.error("Who's the DEV? This should never have happened!", ex));
    }

    private Observable<HttpServer> createRemoteAccessServer() {
        final Router router = Router.router(vertx);

        router.mountSubRouter("/primary", new HierarchicalRouter().create());
        return vertx.createHttpServer(createServerOptions())
                .requestHandler(router)
                .rxListen().toObservable()
                .doOnCompleted(() -> LOGGER.info("Server up and running."))
                .doOnError(e -> LOGGER.error("Unable to launch the server.", e));
    }

    private HttpServerOptions createServerOptions() {
        HttpServerOptions serverOptions = new HttpServerOptions()
                .setPort(serverPort);
        return serverOptions;
    }

    private class HierarchicalRouter {

        private Router create() {
            final Router primaryRouter = Router.router(vertx);
            primaryRouter.route().failureHandler(context -> {
                LOGGER.error("Wohh!! There is a failure..", context.failure());
                context.fail(500, context.failure());
            });
            primaryRouter.route().handler(context -> {
                context.response().setChunked(true);
                LOGGER.info("Handling {} request at first gate. Request Path: {}", context.request().method(), context.request().path());
                context.next();
            });
            if (useSubRouters) {
                LOGGER.info("Mounting handlers via sub-routers.");
                primaryRouter.mountSubRouter("/", attachHandlers(Router.router(vertx)));
                primaryRouter.mountSubRouter("/", attachHandlers(Router.router(vertx)));
            } else {
                LOGGER.info("Mounting handlers onto the main router.");
                attachHandlers(primaryRouter);
                attachHandlers(primaryRouter);
            }
            return primaryRouter;
        }

        private Router attachHandlers(final Router router) {
            router.route("/").handler(this::sendResponse);
            router.route("/abc").handler(this::sendResponse);
            return router;
        }

        private void sendResponse(final RoutingContext context) {
            final String method = context.request().method().name();
            LOGGER.info("Handling {} request at second gate. Request Path: {}", method, context.request().path());
            context.response().setStatusCode(200).write(method + " successful!\n").end();
        }

    }
}
