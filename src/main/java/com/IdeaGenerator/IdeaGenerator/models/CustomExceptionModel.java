package com.IdeaGenerator.IdeaGenerator.models;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class CustomExceptionModel {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;
    private final Exception throwable;

    public CustomExceptionModel(String message, HttpStatus status, ZonedDateTime timestamp, Exception throwable) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.throwable = throwable;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public Exception getThrowable() {
        return throwable;
    }
}
