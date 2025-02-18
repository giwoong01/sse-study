package com.sse.sse_study_backend.notification.exception;

public class SendFailedException extends RuntimeException {
    public SendFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}