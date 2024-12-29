package org.asi.authservice.exception.handler;

import org.asi.exceptionutils.error.ErrorResponse;
import org.asi.exceptionutils.error.Violation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        Map<String, List<String>> errorMessages = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError -> errorMessages
                        .computeIfAbsent(fieldError.getField(), key -> new ArrayList<>())
                        .add(fieldError.getDefaultMessage())
                );

        Set<Violation> validationErrors = errorMessages.entrySet().stream()
                .map(entry -> new Violation(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .violations(validationErrors)
                        .build());
    }

}
