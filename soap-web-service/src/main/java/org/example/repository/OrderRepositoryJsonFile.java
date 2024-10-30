package org.example.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.entity.orders.Order;
import org.example.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Stanislav Hlova
 */
public class OrderRepositoryJsonFile implements OrderRepository {
    private final Logger logger = LoggerFactory.getLogger(OrderRepositoryJsonFile.class);
    private final AtomicReference<BigInteger> lastDecimalIdAtomicReference = new AtomicReference<>(BigInteger.valueOf(0));
    private final ConcurrentHashMap<BigInteger, Order> orderMap;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderRepositoryJsonFile(String jsonFilePath) throws RepositoryException {
        this.orderMap = new ConcurrentHashMap<>();
        List<Order> orders;
        try {
            logger.info("Loading data from {}", jsonFilePath);
            orders = objectMapper.readValue(new File(jsonFilePath), new TypeReference<List<Order>>() {
            });
        } catch (IOException exception) {
            throw new RepositoryException("Can't read records from file during initialization.", exception);
        }
        orders.forEach(order -> orderMap.put(order.getId(), order));
        orders.stream().max(Comparator.comparing(Order::getId)).ifPresent(order -> lastDecimalIdAtomicReference.set(order.getId()));


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            List<Order> orderList = orderMap.values().stream().toList();
            try {
                logger.info("Saving records to the file {}", jsonFilePath);
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                objectMapper.writeValue(new File(jsonFilePath), orderList);
            } catch (IOException exception) {
                throw new RuntimeException(new RepositoryException("Can't save records to the file", exception));
            }
        }));
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orderMap.values());
    }

    @Override
    public Order findById(BigInteger orderId) {
        return orderMap.get(orderId);
    }

    @Override
    public Order add(Order order) {
        BigInteger key = lastDecimalIdAtomicReference.updateAndGet(bigInteger -> bigInteger.add(BigInteger.ONE));
        order.setId(key);
        orderMap.put(key, order);
        return order;
    }

    @Override
    public Order update(BigInteger orderId, Order order) throws RepositoryException {
        if (!orderMap.containsKey(orderId)) {
            throw new RepositoryException("Record with passed id=" + orderId + " can't be found for updating.");
        }
        orderMap.put(orderId, order);
        order.setId(orderId);
        return order;
    }

    @Override
    public void delete(BigInteger orderId) throws RepositoryException {
        if (!orderMap.containsKey(orderId)) {
            throw new RepositoryException("Record with passed id=" + orderId + " can't be found for deleting.");
        }
        orderMap.remove(orderId);
    }
}
