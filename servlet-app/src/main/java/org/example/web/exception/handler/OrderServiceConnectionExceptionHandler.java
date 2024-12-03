package org.example.web.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import org.example.web.exception.OrderServiceConnectionException;

import java.io.IOException;
import java.util.Map;

/**
 * @author Stanislav Hlova
 */
public class OrderServiceConnectionExceptionHandler extends ExceptionHandler<OrderServiceConnectionException> {
    private final ObjectMapper objectMapper;

    public OrderServiceConnectionExceptionHandler(ObjectMapper objectMapper) {
        super(OrderServiceConnectionException.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(ServletRequest request, ServletResponse response, OrderServiceConnectionException throwable) throws IOException {
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        servletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        servletResponse.setContentType(MediaType.APPLICATION_JSON);
        objectMapper.writeValue(response.getOutputStream(), Map.of("message", "Can't connect to order service!  Write to the support."));
    }
}
