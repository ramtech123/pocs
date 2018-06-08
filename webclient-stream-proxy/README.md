POC - WebClient Proxy
=====================

Testing a http proxy which uses `io.vertx.rxjava.ext.web.client.WebClient` for creating server connections. This application has a Client, Proxy and Server each.

When the application starts, client invokes multiple simultaneous POST requests to proxy, which in turn forwards these to the server. The requests received by proxy server can be buffered _(`rxSendBuffer` - read body from client request and write into the server request)_ or streamed _(`rxSendStream` - pass client request object itself to the server request)_. The proxy can also be configured to be in async mode _(client request is paused and resumed)_ or in sync mode _(client request never paused/resumed)_.

*Facing issue with a combination when request streaming is enabled in async mode* - in the proxy layer, client request fails to stream with error message `java.lang.IllegalStateException: Request has already been read`.

**System Requirements:**
- Java 1.8
- Maven 3.x

**How to build:**
Use following maven build command ```mvn clean install```
    
**How to run:**
Starting point for this module is `AppStarter`.

*Flags:*
- `proxyAsBuffer` - when `true`, proxy buffers the request body while proxying it to the server. Default value is `false`.
- `noAsyncProxy` - when `true`, proxy works in _sync mode_, meaning client request is not paused/resumed. Default value is `false`.

 
If running with IDE, above flags can be modified in the first two lines of the `main` method itself by uncommenting appropriate lines.

If running from command line using jar, use below commands to run the application after successfully building the module with maven command:

- Run to stream the request in async mode *_(problem scenario)_*: ```java -jar webclient-stream-proxy-1.0-jar-with-dependencies.jar```
- Run to buffer the request: ```java -jar -DproxyAsBuffer=true webclient-stream-proxy-1.0-jar-with-dependencies.jar```
- Run to proxy the request in sync mode: ```java -jar -DnoAsyncProxy=true webclient-stream-proxy-1.0-jar-with-dependencies.jar```