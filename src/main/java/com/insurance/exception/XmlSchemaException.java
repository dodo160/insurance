package com.insurance.exception;

public class XmlSchemaException extends RuntimeException {

    public XmlSchemaException(String message) {
        super(message);
    }

    public XmlSchemaException(String message, Throwable cause) {
        super(message, cause);
    }
}
