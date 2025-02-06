package com.sse.sse_study_backend.global.error.exception;

public abstract class InvalidGroupException extends RuntimeException {
    public InvalidGroupException(String message) {
        super(message);
    }
}
