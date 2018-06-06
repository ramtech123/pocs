package com.ramtech.vertx.poc.http;

import io.vertx.core.http.HttpMethod;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.rxjava.ext.web.client.HttpRequest;
import io.vertx.rxjava.ext.web.client.WebClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ramtech.vertx.poc.constant.Constants.PROXY_PORT;
import static com.ramtech.vertx.poc.constant.Constants.REQUEST_MAX_RETRY;
import static com.ramtech.vertx.poc.constant.Constants.REQUEST_TIMEOUT_MILLIS;
import static com.ramtech.vertx.poc.util.ClientUtil.getWebClientOptions;
import static java.lang.System.lineSeparator;

public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private WebClient webClient;

    private Client(final Vertx vertx) {
        webClient = WebClient.create(vertx, getWebClientOptions(PROXY_PORT));
    }

    public static Client client(final Vertx vertx) {
        return new Client(vertx);
    }

    public void triggerRequest() throws IOException {
        HttpRequest<Buffer> request = webClient.request(HttpMethod.POST, "/test")
                .timeout(REQUEST_TIMEOUT_MILLIS);
        LOGGER.info("Invoking client request..");
        try (InputStream in = getClass().getResourceAsStream("/client-request/request.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String body = reader.lines().collect(Collectors.joining(lineSeparator()));
            request.rxSendBuffer(Buffer.buffer(body))
                    .retry((count, ex) -> count < REQUEST_MAX_RETRY)
                    .toObservable()
                    .subscribe(response -> {
                        LOGGER.info("Received response with status: {}", response.statusCode());
                        LOGGER.info("Body size = {}", response.body().length());
                        // LOGGER.info("Response Body: {}", response.bodyAsString());
                    }, ex -> LOGGER.error("Received error response: {}", ex.getMessage(), ex));
        }
    }
}
