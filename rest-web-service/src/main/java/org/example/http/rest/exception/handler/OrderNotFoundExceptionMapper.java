package org.example.http.rest.exception.handler;

import jakarta.ws.rs.core.Response;
import org.example.http.rest.exception.OrderNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Stanislav Hlova
 */
public class OrderNotFoundExceptionMapper extends CommonHandler<OrderNotFound> {
    private static final Logger logger = LoggerFactory.getLogger(OrderNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(OrderNotFound exception) {
        logger.debug("Order with id {} wasn't found.", exception.getOrderId());
        String message = "Order with id " + exception.getOrderId() + " wasn't found";
        return super.toResponseBuilder("<error>" + message + "</error>",
                        "{\"error\": \"" + message + "\"}",
                        message,
                        Response.Status.NOT_FOUND)
                .build();

    }
}
