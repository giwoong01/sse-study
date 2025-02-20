package com.sse.sse_study_backend.notification.exception;

import com.sse.sse_study_backend.global.error.exception.InvalidGroupException;

public class MemberAlreadyExistsException extends InvalidGroupException {
    public MemberAlreadyExistsException(String message) {
        super(message);
    }
}
