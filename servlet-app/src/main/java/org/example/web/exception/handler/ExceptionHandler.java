package org.example.web.exception.handler;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

/**
 * @author Stanislav Hlova
 */
public abstract class ExceptionHandler<T> {

    private final Class<T> exceptionClass;

    protected ExceptionHandler(Class<T> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }
    public boolean isSupport(Throwable throwable) {
        return exceptionClass.isAssignableFrom(throwable.getClass());
    }
    public abstract void handle(ServletRequest request, ServletResponse response, T throwable) throws IOException;
}
