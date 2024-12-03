package org.example.web.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.example.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.web.Constant.*;

/**
 * @author Stanislav Hlova
 */
//@WebListener
public class StartupInitializerListener implements ServletContextListener {

    public static final Logger logger = LoggerFactory.getLogger(StartupInitializerListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event)  {
        ServletContext ctx = event.getServletContext();
        logger.trace("Context initialized");
        String restUri = ctx.getInitParameter(REST_SERVICE_URI_PARAM);
        logger.info("Get Init Parameter: rest-uri : {}", restUri);
        if ("".equals(restUri)) {
            throw new IllegalStateException("Can't initialize an OrderService. Set a rest-uri in web.xml.");
        } else {
            ctx.setAttribute(ORDER_SERVICE_SERVLET_CONTEXT_ATTRIBUTE, new OrderService(restUri));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ctx.setAttribute(OBJECT_MAPPER_SERVLET_CONTEXT_ATTRIBUTE, objectMapper);
    }
}
