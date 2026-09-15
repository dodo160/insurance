package com.insurance.exception;

import java.util.List;

public class ErrorResponse {

    private final int status;
    private final String className;
    private final List<String> messages;

    public ErrorResponse(int status, List<String> messages, String className) {
        this.status = status;
        this.className = className;
        this.messages = messages;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getClassName() {
        return className;
    }
}
