package org.example;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.ext.RuntimeDelegate;
import org.example.http.rest.Constants;
import org.example.http.rest.RestApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);


    public static void main(String[] args) throws InterruptedException, IOException {
        logger.info("Initializing Server");
        logger.info("Starting Server");
        // создать сервер, слушающий порт
        final HttpServer server = HttpServer.create(new InetSocketAddress(getBaseURI(Constants.BASE_URI, Constants.PORT, Constants.APPLICATION_PATH).getPort()), 0);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down the server");
            server.stop(0);
            logger.info("Server was shut down");
        }));

        // создать обработчик JAX-RS Application
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new RestApplication(), HttpHandler.class);

        // пусть сервер обрабатывает запросы JAX-RS обработчика по URI
        server.createContext(getBaseURI(Constants.BASE_URI, Constants.PORT, Constants.APPLICATION_PATH).getPath(), handler);

        // запустить сервер
        server.start();

        System.out.println("Application started.\n"
                + "Try accessing " + getBaseURI(Constants.BASE_URI, Constants.PORT, Constants.APPLICATION_PATH, Constants.ORDER_CONTROLLER_PATH) +" in the browser.\n");

        Thread.currentThread().join();

    }

    public static URI getBaseURI(String basePath, int port, String... path) {
        UriBuilder builder = UriBuilder.fromUri(basePath).port(port);
        for (String part : path) {
            builder.path(part);
        }
        URI uri = builder.build();
        System.out.println("uri: " + uri);
        return uri;
    }

    static void printProperties(Map<String, Object> map) {
        Set<String> keys = map.keySet();
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            Object object = (Object) it.next();
            System.out.printf("%s - %s", object, map.get(object));
        }
    }
}
