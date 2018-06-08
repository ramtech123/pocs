package com.ramtech.vertx.poc.constant;

public interface Constants {

    int PROXY_PORT = 8095;
    int SERVER_PORT = 8096;
    int IDLE_TIMEOUT_SECONDS = 60;
    int CONNECT_TIMEOUT_MILLIS = 5000;
    long REQUEST_TIMEOUT_MILLIS = 5000;
    int CONN_POOL_SIZE = 5;
    int REQUEST_MAX_RETRY = 3;
    int MAX_REQUEST_COUNT = 20;
    String PROXY_AS_BUFFER = "proxyAsBuffer";
    String NO_ASYNC_PROXY = "noAsyncProxy";
}
