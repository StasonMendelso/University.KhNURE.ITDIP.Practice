package org.example;

import jakarta.xml.ws.Endpoint;
import org.example.exception.RepositoryException;
import org.example.repository.OrderRepository;
import org.example.repository.OrderRepositoryJsonFile;
import org.example.service.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * @author Stanislav Hlova
 */
public class OrderServiceRunner {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceRunner.class);

    public static final String ADDRESS = "http://localhost:9000/orders";
    public static final String ORDERS_JSON_FILE_PATH = "soap-web-service/src/main/resources/orders.json";

    public static void main(String[] args) throws RepositoryException {
        logger.info("Initializing Server");
        OrderRepository orderRepository = new OrderRepositoryJsonFile(ORDERS_JSON_FILE_PATH);
        Object implementor = new OrderServiceImpl(orderRepository);

        logger.info("Starting Server");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");

        Endpoint endpoint = Endpoint.publish(ADDRESS, implementor);

        logger.info("Server ready... at " + ADDRESS);

        System.err.println("Press <enter> to stop service... ");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        sc.close();
        endpoint.stop();
        logger.info("Server exit");
    }


}
