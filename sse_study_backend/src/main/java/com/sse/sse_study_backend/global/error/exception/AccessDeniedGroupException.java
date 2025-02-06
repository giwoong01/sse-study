package com.sse.sse_study_backend.global.error.exception;

public abstract class AccessDeniedGroupException extends RuntimeException {
    public AccessDeniedGroupException(String message) {
        super(message);
    }
}
