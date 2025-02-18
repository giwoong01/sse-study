package com.sse.sse_study_backend.global.error;

import com.sse.sse_study_backend.global.error.dto.ErrorResponse;
import com.sse.sse_study_backend.global.error.exception.AccessDeniedGroupException;
import com.sse.sse_study_backend.global.error.exception.AuthGroupException;
import com.sse.sse_study_backend.global.error.exception.InvalidGroupException;
import com.sse.sse_study_backend.global.error.exception.NotFoundGroupException;
import com.sse.sse_study_backend.notification.exception.EmitterCallbackException;
import com.sse.sse_study_backend.notification.exception.SendFailedException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    // custom error
    @ExceptionHandler({InvalidGroupException.class})
    public ResponseEntity<ErrorResponse> handleInvalidData(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthGroupException.class})
    public ResponseEntity<ErrorResponse> handleAuthDate(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundGroupException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundDate(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AccessDeniedGroupException.class})
    public ResponseEntity<ErrorResponse> handleAccessDeniedDate(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            EmitterCallbackException.class,
            SendFailedException.class
    })
    public ResponseEntity<ErrorResponse> handleEmitterErrorDate(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Validation 관련 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e) {
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                String.format("%s. (%s)", fieldError.getDefaultMessage(), fieldError.getField()));

        log.error("Validation error for field {}: {}", fieldError.getField(), fieldError.getDefaultMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
