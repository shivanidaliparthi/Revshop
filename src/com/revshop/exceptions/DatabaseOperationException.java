package com.revshop.exceptions;

public class DatabaseOperationException extends Exception {

    private static final long serialVersionUID = 1L;

    public DatabaseOperationException() {
        super();
    }

    public DatabaseOperationException(String message) {
        super(message);
    }
    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}