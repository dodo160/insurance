package com.insurance.common.exception;

public class RequestMismatchException extends RuntimeException {
    public RequestMismatchException(String message) {
        super(message);
    }
}
