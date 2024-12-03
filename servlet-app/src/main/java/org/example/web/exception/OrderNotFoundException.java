package org.example.web.exception;

/**
 * @author Stanislav Hlova
 */
public class OrderNotFoundException extends RuntimeException {
    private final long id;

    public OrderNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
