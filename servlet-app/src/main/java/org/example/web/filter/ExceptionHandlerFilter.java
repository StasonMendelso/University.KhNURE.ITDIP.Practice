package org.example.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import org.example.web.exception.handler.ExceptionHandler;
import org.example.web.exception.handler.OrderNotFoundExceptionHandler;
import org.example.web.exception.handler.OrderServiceConnectionExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.example.web.Constant.OBJECT_MAPPER_SERVLET_CONTEXT_ATTRIBUTE;

/**
 * @author Stanislav Hlova
 */
@WebFilter(urlPatterns = "/*")
public class ExceptionHandlerFilter implements Filter {

    public static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerFilter.class);
    private final List<ExceptionHandler> exceptionHandlerList;
    private ObjectMapper objectMapper;

    public ExceptionHandlerFilter() {
        exceptionHandlerList = new ArrayList<>();

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        objectMapper = (ObjectMapper) servletContext.getAttribute(OBJECT_MAPPER_SERVLET_CONTEXT_ATTRIBUTE);
        exceptionHandlerList.add(new OrderServiceConnectionExceptionHandler(objectMapper));
        exceptionHandlerList.add(new OrderNotFoundExceptionHandler(objectMapper));
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Throwable throwable) {
            Optional<ExceptionHandler> exceptionHandler = getHandler(throwable);
            if (exceptionHandler.isPresent()) {
                logger.error("Registered error was occurred.", throwable);
                exceptionHandler.get().handle(request, response, throwable);
                return;
            }
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            servletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            servletResponse.setContentType(MediaType.APPLICATION_JSON);
            objectMapper.writeValue(response.getOutputStream(), Map.of("message","Unknown error was occurred."));
            logger.error("Unknown/not registered error was occurred.", throwable);
            throw throwable;
        }
    }

    private Optional<ExceptionHandler> getHandler(Throwable throwable) {
        return exceptionHandlerList.stream()
                .filter(exceptionHandler -> exceptionHandler.isSupport(throwable))
                .findFirst();
    }
}
