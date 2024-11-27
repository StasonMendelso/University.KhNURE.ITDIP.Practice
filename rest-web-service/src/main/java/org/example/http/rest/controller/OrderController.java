package org.example.http.rest.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.entity.orders.Order;
import org.example.http.rest.ApplicationConfiguration;
import org.example.http.rest.exception.OrderNotFound;
import org.example.http.rest.locator.OrderLocator;
import org.example.repository.OrderRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.example.http.rest.Constants.ORDER_CONTROLLER_PATH;

/**
 * @author Stanislav Hlova
 */
@Path(ORDER_CONTROLLER_PATH)
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController() {
        this.orderRepository = ApplicationConfiguration.orderRepository();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Order> getOrders(@QueryParam("minTotal") Double minTotal, @QueryParam("maxTotal") Double maxTotal) {
        if(minTotal != null && maxTotal != null){
            return orderRepository.findAll().stream()
                    .filter(order -> {
                        if (order.getOrderItems() == null){
                            return false;
                        }
                        double orderTotal = order.getOrderItems().getOrderItem().stream()
                                .map(orderItem -> orderItem.getTotal().getValue())
                                .reduce(BigDecimal::add).get().doubleValue();
                        return orderTotal >= minTotal && orderTotal <= maxTotal;
                    }).toList();
        }
        return orderRepository.findAll();
    }

    @Path("/{id:\\d+}")
    public OrderLocator orderLocator(@PathParam("id") long id){
        Order order = orderRepository.findById(BigInteger.valueOf(id));
        if (order == null){
            throw new OrderNotFound(id);
        }
        return new OrderLocator(order);
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Order createOrder(Order order) {
        return orderRepository.add(order);
    }


}
