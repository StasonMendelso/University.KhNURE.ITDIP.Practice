package org.example.repository;

import org.example.entity.orders.Order;
import org.example.exception.RepositoryException;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * @author Stanislav Hlova
 */
public interface OrderRepository {
    List<Order> findAll();
    Order findById(BigInteger orderId);
    Order add(Order order);
    Order update(BigInteger orderId, Order order) throws RepositoryException;
    void delete(BigInteger orderId) throws RepositoryException;
}
