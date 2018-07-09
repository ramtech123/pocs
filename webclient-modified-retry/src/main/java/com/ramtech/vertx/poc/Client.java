package com.ramtech.vertx.poc;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.rxjava.ext.web.client.HttpRequest;
import io.vertx.rxjava.ext.web.client.WebClient;

public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private static Vertx vertx;

    public static void init(final Vertx v) {
        vertx = v;
    }

    public static void triggerRequest() {
        WebClient webClient = WebClient.create(vertx, getWebClientOptions(8095));
        invokeRequest(webClient);
    }

    private static void invokeRequest(final WebClient webClient) {
        HttpRequest<Buffer> request = webClient.request(HttpMethod.GET, "/test")
                .timeout(5000);
        AtomicInteger extCounter = new AtomicInteger();
        AtomicBoolean retry = new AtomicBoolean(false);
        request.headers().add("Auth", "Fail");
        request.rxSend()
                .doOnError(ex -> {
                    if (extCounter.get() <=3 ) {
                        retry.set(true);
                        getTokenObs(extCounter.get()).subscribe(token -> {
                            LOGGER.info("Updating the header in request..");
                            request.headers().set("Auth", token);
                        }, exc -> Observable.error(exc));
                    }
                })
                .retry((count, ex) -> {
                    LOGGER.info("Retry logic.. Internal count {}, Ext Count {}", count, extCounter.get());
                    extCounter.incrementAndGet();
                    return retry.get();
                })
                .toObservable()
                .subscribe(response -> {
                            LOGGER.info("Success response..");
                        },
                        ex -> {
                            LOGGER.error("Error response..", ex);
                        },
                        () -> {
                            LOGGER.info("Completed one request. Waiting half second and starting another..");
                            vertx.setTimer(1500, timerId -> triggerRequest());
                        });
    }

    public static WebClientOptions getWebClientOptions(final int targetPort) {
        return new WebClientOptions()
                .setDefaultPort(targetPort)
                .setDefaultHost("localhost")
                .setSsl(false)
                .setMaxPoolSize(2)
                .setConnectTimeout(5000)
                .setIdleTimeout(10000)
                .setKeepAlive(true);
    }

    private static Observable<String> getTokenObs(final int count) {
        LOGGER.info("Sleeping thread for counter.. {}", count);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String s = count == 2 ? "Pass" : "Fail";
        LOGGER.info("Token Value {}", s);
        
        return Observable.just(s);
    }
}
