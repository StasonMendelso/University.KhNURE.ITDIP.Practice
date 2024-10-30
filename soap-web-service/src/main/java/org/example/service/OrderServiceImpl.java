package org.example.service;

import jakarta.jws.HandlerChain;
import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;
import org.example.Constants;
import org.example.entity.orders.Order;
import org.example.exception.RepositoryException;
import org.example.exception.ServiceException;
import org.example.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Stanislav Hlova
 */
@HandlerChain(file = "security_handler.xml")
@WebService(serviceName = "Orders",
        portName = "OrderPort",
        endpointInterface = "org.example.service.OrderService",
        targetNamespace = Constants.ORDER_SERVICE_NAMESPACE)
public class OrderServiceImpl implements OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAll(String clientToken, Holder<String> tokenExpiresAt) throws ServiceException {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(BigInteger orderId, String clientToken, Holder<String> tokenExpiresAt) throws ServiceException {
        if (orderId == null) {
            throw new ServiceException("OrderId can't be null");
        }
        return orderRepository.findById(orderId);
    }

    @Override
    public Order add(Order order, String clientToken, Holder<String> tokenExpiresAt) throws ServiceException {
        if (order == null) {
            throw new ServiceException("Order can't be null");
        }
        return orderRepository.add(order);
    }

    @Override
    public Order update(BigInteger orderId, Order order, String clientToken, Holder<String> tokenExpiresAt) throws ServiceException {
        if (orderId == null) {
            throw new ServiceException("OrderId can't be null");
        }
        if (order == null) {
            throw new ServiceException("Order can't be null");
        }
        try {
            return orderRepository.update(orderId, order);
        } catch (RepositoryException exception) {
            logger.error("Can't update a record.",exception);
            throw new ServiceException(exception.getMessage());
        }
    }

    @Override
    public void delete(BigInteger orderId, String clientToken, Holder<String> tokenExpiresAt) throws ServiceException {
        if (orderId == null) {
            throw new ServiceException("OrderId can't be null");
        }
        try {
            orderRepository.delete(orderId);
        } catch (RepositoryException exception) {
            logger.error("Can't delete a record.",exception);
            throw new ServiceException(exception.getMessage());
        }
    }
}
