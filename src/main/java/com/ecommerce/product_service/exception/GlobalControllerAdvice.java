package com.ecommerce.product_service.exception;

import ch.qos.logback.classic.util.LogbackMDCAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    // Para manejar errores de recursos no encontrados
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException EX, WebRequest request) {

        log.warn("Resource not found: {}", EX.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, EX.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setDetail(EX.getMessage());
        problemDetail.setType(URI.create("http://api.ecommerce.com/errors/not-found"));
        problemDetail.setProperty("Timestamp", Instant.now());

        problemDetail.setProperty("Resource Name", EX.getResourceName());
        problemDetail.setProperty("Field Name", EX.getFieldName());
        problemDetail.setProperty("Field Value", EX.getFieldValue());

        return problemDetail;
    }

    // Para manejar errores de validacin de campos en las solicitudes
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException EX) {

        log.error("Validation error: {}", EX.getMessage(), EX);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed for one or more fields.");

        problemDetail.setTitle("Validation Error");
        problemDetail.setType(URI.create("http://api.ecommerce.com/errors/validation-error"));
        problemDetail.setProperty("Timestamp", Instant.now());

        Map<String, String> errorMap = new HashMap<>();

        // Recorremos los errores y los vamos metiendo al mismo mapa
        EX.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        problemDetail.setProperty("errors", errorMap);

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception EX) {

        log.error("An unexpected error occurred: {}", EX.getMessage(), EX);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");

        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("http://api.ecommerce.com/errors/internal-server-error"));
        problemDetail.setProperty("Timestamp", Instant.now());

        return problemDetail;
    }
}

