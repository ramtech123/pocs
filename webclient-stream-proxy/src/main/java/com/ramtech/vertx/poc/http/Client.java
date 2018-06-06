package com.ramtech.vertx.poc.http;

import io.vertx.core.http.HttpMethod;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.rxjava.ext.web.client.HttpRequest;
import io.vertx.rxjava.ext.web.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ramtech.vertx.poc.constant.Constants.PROXY_PORT;
import static com.ramtech.vertx.poc.constant.Constants.REQUEST_MAX_RETRY;
import static com.ramtech.vertx.poc.constant.Constants.REQUEST_TIMEOUT_MILLIS;
import static com.ramtech.vertx.poc.util.ClientUtil.getWebClientOptions;

public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private WebClient webClient;

    private Client(final Vertx vertx) {
        webClient = WebClient.create(vertx, getWebClientOptions(PROXY_PORT));
    }

    public static Client client(final Vertx vertx) {
        return new Client(vertx);
    }

    public void triggerRequest() {
        HttpRequest<Buffer> request = webClient.request(HttpMethod.POST, "/test")
                .timeout(REQUEST_TIMEOUT_MILLIS);
        LOGGER.info("Invoking client request..");
        request.rxSendBuffer(Buffer.buffer("Sample request body"))
                .retry((count, ex) -> count < REQUEST_MAX_RETRY)
                .toObservable()
                .subscribe(response -> {
                    LOGGER.info("Received response with status: {}", response.statusCode());
                    LOGGER.info("Response Body: {}", response.bodyAsString());
                }, ex -> LOGGER.error("Received error response: {}", ex.getMessage(), ex));
    }
}
