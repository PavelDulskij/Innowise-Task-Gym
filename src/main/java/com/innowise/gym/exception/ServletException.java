package com.innowise.gym.exception;

public class ServletException extends Exception {
    public ServletException(String message) {
        super(message);
    }
    public ServletException(Throwable e) {
        super(e);
    }
    public ServletException(Throwable e, String message) {
        super(message, e);
    }
}
