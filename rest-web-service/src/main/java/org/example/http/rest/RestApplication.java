package org.example.http.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.example.http.rest.controller.OrderController;
import org.example.http.rest.exception.handler.GlobalExceptionMapper;
import org.example.http.rest.exception.handler.OrderNotFoundExceptionMapper;
import org.example.http.rest.reader.OrderJSONMessageBodyReader;
import org.example.http.rest.reader.OrderXMLMessageBodyReader;
import org.example.http.rest.writer.OrderListJSONMessageBodyWriter;
import org.example.http.rest.writer.OrderListXMLMessageBodyWriter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stanislav Hlova
 */
@ApplicationPath(Constants.APPLICATION_PATH)
public class RestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(OrderController.class);
        //Writers
        classes.add(OrderListJSONMessageBodyWriter.class);
        classes.add(OrderListXMLMessageBodyWriter.class);
        //Readers
        classes.add(OrderJSONMessageBodyReader.class);
        classes.add(OrderXMLMessageBodyReader.class);
        //Exception mapper
        classes.add(OrderNotFoundExceptionMapper.class);
        classes.add(GlobalExceptionMapper.class);
        return Collections.unmodifiableSet(classes);
    }

}
