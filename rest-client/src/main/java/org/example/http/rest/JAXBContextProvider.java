package org.example.http.rest;

import jakarta.ws.rs.ext.ContextResolver;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Stanislav Hlova
 */
public class JAXBContextProvider implements ContextResolver<JAXBContext> {
    private static final Logger logger = LoggerFactory.getLogger(JAXBContextProvider.class);

    private JAXBContext context = null;

    public JAXBContext getContext(Class<?> type) {
        if (context == null) {
            try {
                context = JAXBContext.newInstance (org.example.entity.ObjectFactory.class, org.example.entity.shoe.ObjectFactory.class,  org.example.entity.orders.ObjectFactory.class);
            } catch (JAXBException exception) {
                logger.error("Error during creating a JAXBContext.", exception);
            }
        }
        return context;
    }
}
