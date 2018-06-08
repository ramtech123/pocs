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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ramtech.vertx.poc.constant.Constants.MAX_REQUEST_COUNT;
import static com.ramtech.vertx.poc.constant.Constants.PROXY_PORT;
import static com.ramtech.vertx.poc.constant.Constants.REQUEST_MAX_RETRY;
import static com.ramtech.vertx.poc.constant.Constants.REQUEST_TIMEOUT_MILLIS;
import static com.ramtech.vertx.poc.util.ClientUtil.getWebClientOptions;
import static java.lang.System.lineSeparator;

public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private List<WebClient> webClients = new ArrayList<>();
    AtomicInteger successCount = new AtomicInteger(0);
    AtomicInteger failureCount = new AtomicInteger(0);
    AtomicInteger requestCount = new AtomicInteger(0);
    AtomicInteger responseCount = new AtomicInteger(0);

    private Client(final Vertx vertx) {
        for (int i = 0; i < MAX_REQUEST_COUNT; i++) {
            webClients.add(WebClient.create(vertx, getWebClientOptions(PROXY_PORT)));
        }
    }

    public static Client client(final Vertx vertx) {
        return new Client(vertx);
    }

    public void triggerRequest() {
        try (InputStream in = getClass().getResourceAsStream("/client-request/request.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String body = reader.lines().collect(Collectors.joining(lineSeparator()));
            webClients.forEach(webClient -> {
                invokeRequest(webClient, body);
            });
        } catch (IOException ex) {
            LOGGER.error("Error while reading request file: {}", ex.getMessage(), ex);
        }
    }

    private void invokeRequest(final WebClient webClient, final String body) {
        HttpRequest<Buffer> request = webClient.request(HttpMethod.POST, "/test")
                .timeout(REQUEST_TIMEOUT_MILLIS);
        requestCount.incrementAndGet();
        request.rxSendBuffer(Buffer.buffer(body))
                .retry((count, ex) -> count < REQUEST_MAX_RETRY)
                .toObservable()
                .subscribe(response -> {
                            if (response.statusCode() < 400) {
                                successCount.incrementAndGet();
                            } else {
                                failureCount.incrementAndGet();
                            }
                            LOGGER.info("Body size = {}, Status: {}, SuccessCount = {}, FailureCount = {}", response.body().length(),
                                    response.statusCode(), successCount.get(), failureCount.get());
                        },
                        ex -> {
                            LOGGER.error("Error response: {}, SuccessCount = {}, FailureCount = {}", ex.getMessage(),
                                    successCount.get(), failureCount.incrementAndGet(), ex);
                        },
                        () -> {
                            responseCount.incrementAndGet();
                            LOGGER.info("RequestCount = {}, ResponseCount = {}", requestCount.get(), responseCount.get());
                        });
    }
}
