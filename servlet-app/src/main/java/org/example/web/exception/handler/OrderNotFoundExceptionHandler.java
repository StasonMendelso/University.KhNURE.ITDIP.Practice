package org.example.web.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import org.example.web.exception.OrderNotFoundException;

import java.io.IOException;
import java.util.Map;

/**
 * @author Stanislav Hlova
 */
public class OrderNotFoundExceptionHandler extends ExceptionHandler<OrderNotFoundException> {
    private final ObjectMapper objectMapper;

    public OrderNotFoundExceptionHandler(ObjectMapper objectMapper) {
        super(OrderNotFoundException.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(ServletRequest request, ServletResponse response, OrderNotFoundException throwable) throws IOException {
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        servletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        servletResponse.setContentType(MediaType.APPLICATION_JSON);
        objectMapper.writeValue(response.getOutputStream(), Map.of("message", "Order with id = " + throwable.getId() + " not found."));
    }
}
