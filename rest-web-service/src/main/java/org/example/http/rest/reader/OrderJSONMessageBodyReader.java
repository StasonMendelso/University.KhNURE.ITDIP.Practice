package org.example.http.rest.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.MessageBodyReader;
import org.example.entity.orders.Order;
import org.example.http.rest.ApplicationConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author Stanislav Hlova
 */
public class OrderJSONMessageBodyReader implements MessageBodyReader<Order> {

    private final ObjectMapper objectMapper;

    public OrderJSONMessageBodyReader() {
        this.objectMapper = ApplicationConfiguration.objectMapper();
    }


    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == Order.class && mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public Order readFrom(Class<Order> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                          jakarta.ws.rs.core.MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException {
        return objectMapper.readValue(entityStream, Order.class);
    }
}
