package org.example.http.rest.exception.handler;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Stanislav Hlova
 */
public class GlobalExceptionMapper extends CommonHandler<Throwable>{
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionMapper.class);
    @Context
    private UriInfo uriInfo;
    @Override
    public Response toResponse(Throwable exception) {
        String requestUri = (uriInfo != null) ? uriInfo.getRequestUri().toString() : "Unknown endpoint";
        String httpMethod = (uriInfo != null) ? uriInfo.getPath() : "Unknown HTTP method";

        logger.error("Exception occurred on endpoint [{}] (HTTP Method: {}).", requestUri, httpMethod, exception);

        String message = "An unknown exception occurred while processing the request.";
        String details = String.format("Error occurred on endpoint [%s] (HTTP Method: %s).", requestUri, httpMethod);

        return super.toResponseBuilder(
                "<error>" + message + "</error>",
                "{\"error\": \"" + message + "\", \"details\": \"" + details + "\"}",
                details,
                Response.Status.INTERNAL_SERVER_ERROR
        ).build();
    }
}
