package com.ecommerce.product_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException EX, WebRequest request) {
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
}
