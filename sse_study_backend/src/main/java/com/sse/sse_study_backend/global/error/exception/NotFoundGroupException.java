package com.sse.sse_study_backend.global.error.exception;

public abstract class NotFoundGroupException extends RuntimeException {
    public NotFoundGroupException(String message) {
        super(message);
    }
}
