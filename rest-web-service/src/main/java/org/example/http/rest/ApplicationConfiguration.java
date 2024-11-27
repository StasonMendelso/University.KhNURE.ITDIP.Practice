package org.example.http.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.RepositoryException;
import org.example.repository.OrderRepository;
import org.example.repository.OrderRepositoryJsonFile;

/**
 * @author Stanislav Hlova
 */
public class ApplicationConfiguration {
    public static final String ORDERS_JSON_FILE_PATH = "rest-web-service/src/main/resources/orders.json";
    private static final OrderRepository ORDER_REPOSITORY;
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        try {
            ORDER_REPOSITORY = new OrderRepositoryJsonFile(ORDERS_JSON_FILE_PATH);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    public static OrderRepository orderRepository(){
        return ORDER_REPOSITORY;
    }

    public static ObjectMapper objectMapper() {
        return OBJECT_MAPPER;
    }
}
