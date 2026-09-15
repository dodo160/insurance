package com.insurance.exception;

public class XmlMarshallingException extends RuntimeException {
    public XmlMarshallingException(String message) {
        super(message);
    }

    public XmlMarshallingException(String message, Throwable cause) {
        super(message, cause);
    }
}
