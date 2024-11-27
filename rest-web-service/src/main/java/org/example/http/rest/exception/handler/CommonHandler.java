package org.example.http.rest.exception.handler;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.List;

/**
 * @author Stanislav Hlova
 */
public abstract class CommonHandler<T extends Throwable> implements ExceptionMapper<T> {
    @Context
    private HttpHeaders httpHeaders;

    public Response.ResponseBuilder toResponseBuilder(String xmlMessage, String jsonMessage, String plainMessage, Response.Status status) {
        List<MediaType> acceptableMediaTypes = httpHeaders.getAcceptableMediaTypes();

        for (MediaType mediaType : acceptableMediaTypes) {
            if (mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE)) {
                return Response.status(status)
                        .entity(jsonMessage)
                        .type(MediaType.APPLICATION_JSON);
            } else if (mediaType.isCompatible(MediaType.APPLICATION_XML_TYPE)) {
                return Response.status(status)
                        .entity(xmlMessage)
                        .type(MediaType.APPLICATION_XML);
            }
        }

        return Response.status(status)
                .entity(plainMessage)
                .type(MediaType.TEXT_PLAIN);
    }
}
