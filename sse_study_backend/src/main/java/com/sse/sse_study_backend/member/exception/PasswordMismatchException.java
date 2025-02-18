package com.sse.sse_study_backend.member.exception;

import com.sse.sse_study_backend.global.error.exception.InvalidGroupException;

public class PasswordMismatchException extends InvalidGroupException {
    public PasswordMismatchException(String message) {
        super(message);
    }

    public PasswordMismatchException() {
        this("패스워드가 일치하지 않습니다.");
    }
}
