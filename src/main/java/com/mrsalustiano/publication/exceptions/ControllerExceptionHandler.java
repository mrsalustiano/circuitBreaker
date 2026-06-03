package com.mrsalustiano.publication.exceptions;

import com.mrsalustiano.publication.domain.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(
            ResponseStatusException exception,
            HttpServletRequest request
    ) {
        var status = exception.getStatusCode();
        var message = exception.getReason() != null
                ? exception.getReason()
                : status.toString();

        var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(status.value())
                .message(message)
                .path(request.getRequestURI())
                .build();

        if (status.is5xxServerError()) {
            log.warn("[{}] {}", status.value(), errorResponse);
        } else {
            log.debug("[{}] {}", status.value(), errorResponse);
        }

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception exception,
            HttpServletRequest request
    ) {
        var errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();

        log.error("[500] {}", errorResponse, exception);
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
