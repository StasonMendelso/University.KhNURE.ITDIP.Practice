package org.example.service;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entity.orders.Order;
import org.example.web.exception.OrderNotFoundException;
import org.example.web.exception.OrderServiceConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Stanislav Hlova
 */
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final WebTarget target;

    public OrderService(String baseUri) {
        Client client = ClientBuilder.newClient();
        this.target = client.target(baseUri);
    }

    public List<Order> getAllOrders() {
        logger.info("Fetching all orders...");
        return handleConnection(() -> {
            Response response = target.request(MediaType.APPLICATION_JSON).get();
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(new GenericType<List<Order>>() {
                });
            }
            logger.error("Failed to fetch all orders. Status: {}", response.getStatus());
            return null;
        });
    }


    public Order getOrderById(long id) {
        logger.info("Fetching order with ID {}...", id);
        return handleConnection(() -> {
            Response response = target.path("/" + id).request(MediaType.APPLICATION_JSON).get();
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(Order.class);
            } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                logger.warn("Order with ID {} not found. Server response: {}", id, response.readEntity(String.class));
                throw new OrderNotFoundException(id);
            } else {
                logger.error("Failed to fetch order. Status: {}", response.getStatus());
                return null;
            }
        });

    }

    public List<Order> getFilteredOrders(double minTotal, double maxTotal) {
        logger.info("Fetching orders with total between {} and {}...", minTotal, maxTotal);
        return handleConnection(() -> {
            Response response = target
                    .queryParam("minTotal", minTotal)
                    .queryParam("maxTotal", maxTotal)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(new GenericType<List<Order>>() {
                });
            }
            logger.error("Failed to fetch filtered orders. Status: {}", response.getStatus());
            return null;
        });

    }

    public Order createOrder(Order order) {
        logger.info("Creating a new order...");
        return handleConnection(() -> {
            Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(order, MediaType.APPLICATION_JSON));
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(Order.class);
            }
            logger.error("Failed to create order. Status: {}", response.getStatus());
            return null;
        });
    }

    public Order updateOrder(long id, Order order) {
        logger.info("Updating order with ID {}...", id);
        return handleConnection(() -> {
            Response response = target.path("/" + id).request(MediaType.APPLICATION_JSON).put(Entity.entity(order, MediaType.APPLICATION_JSON));
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(Order.class);
            } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                logger.warn("Order with ID {} not found for deletion.", id);
                throw new OrderNotFoundException(id);
            } else {
                logger.error("Failed to update order. Status: {}. Response {}", response.getStatus(), response.readEntity(String.class));
            }
            return null;
        });
    }

    public boolean deleteOrder(long id) {
        logger.info("Deleting order with ID {}...", id);
        return handleConnection(() -> {
            Response response = target.path("/" + id).request(MediaType.APPLICATION_JSON).delete();
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                logger.info("Order deleted successfully.");
                return true;
            } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                logger.warn("Order with ID {} not found for deletion.", id);
                throw new OrderNotFoundException(id);
            } else {
                logger.error("Failed to delete order. Status: {}", response.getStatus());
            }
            return false;
        });

    }

    public static <T> T handleConnection(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (ProcessingException processingException) {
            logger.error("Processing error was occurred.", processingException);
            if (processingException.getCause().getClass() == ConnectException.class) {
                throw new OrderServiceConnectionException();
            }
            throw processingException;
        }
    }
}
