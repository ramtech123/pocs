package com.ramtech.vertx.poc.http;

import io.vertx.core.http.HttpMethod;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.client.HttpRequest;
import io.vertx.rxjava.ext.web.client.HttpResponse;
import io.vertx.rxjava.ext.web.client.WebClient;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Single;

import static com.ramtech.vertx.poc.constant.Constants.*;
import static com.ramtech.vertx.poc.util.ClientUtil.getWebClientOptions;
import static com.ramtech.vertx.poc.util.ServerUtil.createHttpServer;
import static com.ramtech.vertx.poc.util.ServerUtil.getServerOptions;
import static java.lang.Boolean.getBoolean;

/**
 * Server - listens for HTTP POST requests on port 8095, and proxies request to the server, and responds back to client.
 */
public class Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(Proxy.class);

    private static Vertx vertx;

    public static Single<HttpServer> httpProxy(final Vertx vertx) {
        Proxy.vertx = vertx;
        boolean proxyAsBuffer = getBoolean(PROXY_AS_BUFFER);
        LOGGER.info("Read configuration proxyAsBuffer = {}", proxyAsBuffer);
        return createHttpServer(vertx, getServerOptions(PROXY_PORT), "'Proxy'", proxyAsBuffer, Proxy::handleRequest);
    }

    /**
     * Handle the request and proxy it to the server.
     * <p>
     * If {@code noAsyncProxy = true}, request is proxied directly with no delay. Otherwise, request is paused for a
     * simulated delay, and resumed in an async block before proxying.
     *
     * @param context
     */
    private static void handleRequest(final RoutingContext context) {
        if (getBoolean(NO_ASYNC_PROXY)) {
            LOGGER.info("Async proxy is not enabled.");
            doProxy(context);
        } else {
            LOGGER.info("Async proxy enabled. Simulating a delay before proxying..");
            context.request().pause();
            Single.just("Dummy Value").delay(100, TimeUnit.MILLISECONDS).subscribe(string -> {
                context.request().resume();
                doProxy(context);
            }, ex -> LOGGER.error("If this block executes, it has to be the topmost wornder of the world!", ex));
        }
    }

    /**
     * Create a webclient and send request to the server.
     * <p>
     * If {@code proxyAsBuffer = true}, {@code rxSendBuffer} is used with client request body. Otherwise, {@code
     * rxSendStream} is used with client request, which is instance of {@code ReadStream<Buffer>}
     *
     * @param context
     */
    private static void doProxy(final RoutingContext context) {
        LOGGER.info("Proxying the request ...");
        final WebClient proxyClient = WebClient.create(vertx, getWebClientOptions(SERVER_PORT));
        HttpRequest<Buffer> request = proxyClient.request(HttpMethod.POST, context.request().path())
                .timeout(REQUEST_TIMEOUT_MILLIS);
        request.headers().addAll(context.request().headers());

        if (getBoolean(PROXY_AS_BUFFER)) {
            LOGGER.info("Proxying request as buffer..");
            request.rxSendBuffer(context.getBody())
                    .retry((count, ex) -> count < REQUEST_MAX_RETRY)
                    .toObservable()
                    .subscribe(response -> handleResponse(context, response),
                            ex -> handleError(context, ex));
        } else {
            LOGGER.info("Proxying request as stream..");
            request.rxSendStream(context.request())
                    .retry((count, ex) -> count < REQUEST_MAX_RETRY)
                    .toObservable()
                    .subscribe(response -> handleResponse(context, response),
                            ex -> handleError(context, ex));
        }
    }

    private static void handleResponse(final RoutingContext context, final HttpResponse response) {
        LOGGER.info("Received response at proxy. Responding back to client..");
        context.response()
                .headers().addAll(response.headers());
        context.response().setChunked(true)
                .setStatusCode(response.statusCode())
                .setStatusMessage(response.statusMessage())
                .write(response.bodyAsString())
                .end();
    }

    private static void handleError(final RoutingContext context, final Throwable ex) {
        LOGGER.info("Received failed response at proxy. Responding back to client..");
        context.response().setChunked(true)
                .setStatusCode(500)
                .setStatusMessage("Request processing failed: " + ex.getMessage());
        context.fail(ex);
    }
}
