package com.sse.sse_study_backend.global.error.exception;

public abstract class AuthGroupException extends RuntimeException {
    public AuthGroupException(String message) {
        super(message);
    }
}
