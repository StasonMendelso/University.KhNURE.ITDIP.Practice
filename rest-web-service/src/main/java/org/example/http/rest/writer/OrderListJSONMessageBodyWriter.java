package org.example.http.rest.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import org.example.entity.orders.Order;
import org.example.http.rest.ApplicationConfiguration;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Stanislav Hlova
 */
public class OrderListJSONMessageBodyWriter implements MessageBodyWriter<List<Order>> {
    private final ObjectMapper objectMapper;

    public OrderListJSONMessageBodyWriter() {
        this.objectMapper = ApplicationConfiguration.objectMapper();
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        if (!mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE)) {
            return false;
        }
        if (List.class.isAssignableFrom(aClass) && type instanceof ParameterizedType) {
            Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];
            return actualType == Order.class;
        }

        return false;
    }

    @Override
    public void writeTo(List<Order> orders, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        objectMapper.writeValue(outputStream, orders);
    }
}
