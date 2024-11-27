package org.example.http.rest.writer;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.example.entity.orders.ObjectFactory;
import org.example.entity.orders.Order;
import org.example.entity.orders.Orders;
import org.example.http.rest.util.CustomNamespacePrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static org.example.http.rest.Constants.ORDERS_NAMESPACE_URI;

/**
 * @author Stanislav Hlova
 */
public class OrderListXMLMessageBodyWriter implements MessageBodyWriter<List<Order>> {
    private static final Logger logger = LoggerFactory.getLogger(OrderListXMLMessageBodyWriter.class);
    private final JAXBContext jaxbContext;
    private static final QName qName = new QName(ORDERS_NAMESPACE_URI, "orders");

    public OrderListXMLMessageBodyWriter() throws JAXBException {
        jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        if (!mediaType.isCompatible(MediaType.APPLICATION_XML_TYPE)) {
            return false;
        }
        if (List.class.isAssignableFrom(aClass) && type instanceof ParameterizedType) {
            Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];
            return actualType == Order.class;
        }

        return false;
    }

    @Override
    public void writeTo(List<Order> orders, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        OutputStreamWriter writer = new OutputStreamWriter(entityStream, "UTF-8");


        try {
            Orders orders1 = new Orders();
            orders1.setOrderList(orders);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", new CustomNamespacePrefixMapper());
            JAXBElement<Orders> root = new JAXBElement<>(qName, Orders.class, orders1);
            marshaller.marshal(root, writer);
        } catch (JAXBException exception) {
            logger.error("Error occurred during mapping a response to XML", exception);
            throw new WebApplicationException(exception);
        }
        writer.close();
    }

}
