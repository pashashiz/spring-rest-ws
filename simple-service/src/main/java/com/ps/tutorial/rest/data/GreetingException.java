package com.ps.tutorial.rest.data;

public class GreetingException extends Exception {

    public GreetingException() {
        super();
    }

    public GreetingException(String message) {
        super(message);
    }

    public GreetingException(String message, Throwable cause) {
        super(message, cause);
    }

    public GreetingException(Throwable cause) {
        super(cause);
    }

    protected GreetingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
