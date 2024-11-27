package org.example.http.rest.reader;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.example.entity.orders.ObjectFactory;
import org.example.entity.orders.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author Stanislav Hlova
 */
public class OrderXMLMessageBodyReader implements MessageBodyReader<Order> {
    private static final Logger logger = LoggerFactory.getLogger(OrderXMLMessageBodyReader.class);
    private final JAXBContext jaxbContext;

    public OrderXMLMessageBodyReader() {
        try {
            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        } catch (JAXBException exception) {
            logger.error("Error initializing JAXBContext", exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == Order.class && mediaType.isCompatible(MediaType.APPLICATION_XML_TYPE);
    }

    @Override
    public Order readFrom(Class<Order> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                          jakarta.ws.rs.core.MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws WebApplicationException {
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            // Використовуємо StAX (Streaming API for XML) для обробки потоку
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(entityStream);

            // Демаршалимо XML у Order
            JAXBElement<Order> root = unmarshaller.unmarshal(xmlStreamReader, Order.class);
            return root.getValue(); // Повертаємо Order
        } catch (JAXBException | XMLStreamException exception) {
            logger.error("Error occurred during deserialization of XML into Order object", exception);
            throw new WebApplicationException(exception);
        }
    }
}
