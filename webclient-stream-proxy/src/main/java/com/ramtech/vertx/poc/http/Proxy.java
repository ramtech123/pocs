package com.ramtech.vertx.poc.http;

import io.vertx.core.http.HttpMethod;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.client.HttpRequest;
import io.vertx.rxjava.ext.web.client.HttpResponse;
import io.vertx.rxjava.ext.web.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Single;

import static com.ramtech.vertx.poc.constant.Constants.PROXY_AS_BUFFER;
import static com.ramtech.vertx.poc.constant.Constants.PROXY_PORT;
import static com.ramtech.vertx.poc.constant.Constants.REQUEST_MAX_RETRY;
import static com.ramtech.vertx.poc.constant.Constants.REQUEST_TIMEOUT_MILLIS;
import static com.ramtech.vertx.poc.constant.Constants.SERVER_PORT;
import static com.ramtech.vertx.poc.util.ClientUtil.getWebClientOptions;
import static com.ramtech.vertx.poc.util.ServerUtil.createHttpServer;
import static com.ramtech.vertx.poc.util.ServerUtil.getServerOptions;
import static java.lang.Boolean.getBoolean;

public class Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(Proxy.class);

    private static WebClient proxyClient;

    public static Single<HttpServer> httpProxy(final Vertx vertx) {
        proxyClient = WebClient.create(vertx, getWebClientOptions(SERVER_PORT));
        boolean proxyAsBuffer = getBoolean(PROXY_AS_BUFFER);
        LOGGER.info("Read configuration proxyAsBuffer = {}", proxyAsBuffer);
        return createHttpServer(vertx, getServerOptions(PROXY_PORT), "'Proxy'", proxyAsBuffer, Proxy::handleRequest);
    }

    private static void handleRequest(final RoutingContext context) {
        LOGGER.info("Proxying the request received via path {}", context.request().path());
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
