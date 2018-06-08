package com.ramtech.vertx.poc.util;

import io.vertx.ext.web.client.WebClientOptions;

import static com.ramtech.vertx.poc.constant.Constants.CONNECT_TIMEOUT_MILLIS;
import static com.ramtech.vertx.poc.constant.Constants.CONN_POOL_SIZE;
import static com.ramtech.vertx.poc.constant.Constants.IDLE_TIMEOUT_SECONDS;

public class ClientUtil {

    public static WebClientOptions getWebClientOptions(final int targetPort) {
        return new WebClientOptions()
                .setDefaultPort(targetPort)
                .setDefaultHost("localhost")
                .setSsl(false)
                .setMaxPoolSize(CONN_POOL_SIZE)
                .setConnectTimeout(CONNECT_TIMEOUT_MILLIS)
                .setIdleTimeout(IDLE_TIMEOUT_SECONDS)
                .setKeepAlive(true)
                .setMaxChunkSize(15);
    }
    
}
