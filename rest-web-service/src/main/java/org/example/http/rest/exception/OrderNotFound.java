package org.example.http.rest.exception;

/**
 * @author Stanislav Hlova
 */
public class OrderNotFound extends RuntimeException{
    private final long orderId;
    public OrderNotFound(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderId() {
        return orderId;
    }
}
