package org.example.http.rest.locator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.entity.orders.*;
import org.example.http.rest.ApplicationConfiguration;
import org.example.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Stanislav Hlova
 */

public class OrderLocator {
    private final Order order;
    private final OrderRepository orderRepository;

    public OrderLocator(Order order) {
        this.order = order;
        this.orderRepository = ApplicationConfiguration.orderRepository();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Order get() {
        return order;
    }

    @GET
    @Path("/buyer")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Buyer getBuyer() {
        return order.getBuyer();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Order deleteOrderById() {
        return orderRepository.delete(order.getId());
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Order updateOrder(Order order) {
        Stream<OrderItem> stream = order.getOrderItems().getOrderItem().stream()
                .map(orderItem -> {
                    if (orderItem.getDiscount() == null || orderItem.getDiscount().getValue() == null) {
                        orderItem.setDiscount(new Discount(BigDecimal.valueOf(0),"UAH"));
                    }
                    BigDecimal total = orderItem.getShoe().getPrice().getValue().multiply(BigDecimal.valueOf(orderItem.getCount().getValue())).subtract(orderItem.getDiscount().getValue());
                    orderItem.getTotal()
                            .setValue(total);
                    return orderItem;
                });
        OrderItems value = new OrderItems();
        value.setOrderItem(stream.toList());
        order.setOrderItems(value);
        return orderRepository.update(this.order.getId(), order);
    }


    @PATCH
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Order patchOrder(Map<String, Object> updates) {
        updates.forEach((field, value) -> {
            switch (field) {
                case "buyer":
                    if (value == null) {
                        order.setBuyer(null);
                    } else {
                        Buyer buyer = parseToEntity(value, Buyer.class);
                        order.setBuyer(buyer);
                    }
                    break;

                case "receiver":
                    if (value == null) {
                        order.setReceiver(null);
                    } else {
                        Receiver receiver = parseToEntity(value, Receiver.class);
                        order.setReceiver(receiver);
                    }
                    break;

                case "orderItems":
                    if (value == null) {
                        order.setOrderItems(null);
                    } else {
                        List<OrderItem> orderItems = parseToList(value, OrderItem.class);
                        OrderItems orderItems1 = new OrderItems();
                        orderItems1.setOrderItem(orderItems);
                        order.setOrderItems(orderItems1);
                    }
                    break;

                case "status":
                    order.setStatus(OrderStatus.fromValue((String) value));
                    break;
                default:
                    throw new BadRequestException("Поле '" + field + "' не може бути оновлено або не існує");
            }
        });

        return orderRepository.update(order.getId(), order);

    }

    private <T> T parseToEntity(Object value, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(value, clazz);
    }

    private <T> List<T> parseToList(Object value, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(value, new TypeReference<List<T>>() {
        });
    }


}
