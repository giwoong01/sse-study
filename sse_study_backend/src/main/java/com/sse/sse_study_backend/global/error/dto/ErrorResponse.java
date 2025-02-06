package com.sse.sse_study_backend.global.error.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {
}