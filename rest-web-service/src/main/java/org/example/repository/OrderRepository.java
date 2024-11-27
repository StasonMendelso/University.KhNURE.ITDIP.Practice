package org.example.repository;

import org.example.entity.orders.Order;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Stanislav Hlova
 */
public interface OrderRepository {
    List<Order> findAll();
    Order findById(BigInteger orderId);
    Order add(Order order);
    Order update(BigInteger orderId, Order order);
    Order delete(BigInteger orderId);
}
