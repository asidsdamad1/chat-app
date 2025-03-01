package org.asi.exceptionutils;

import jakarta.servlet.http.HttpServletRequest;
import org.asi.exceptionutils.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public interface CommonExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    default ErrorResponse handleNotFound(final NotFoundException ex, HttpServletRequest request) {
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .detail(ex.getMessage())
                .path(request.getRequestURI())
                .title(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build();
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    default ErrorResponse handleAlreadyExists(final AlreadyExistsException ex, HttpServletRequest request) {
        return ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .detail(ex.getMessage())
                .path(request.getRequestURI())
                .title(HttpStatus.CONFLICT.getReasonPhrase())
                .build();
    }

    @ExceptionHandler(value = InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    default ErrorResponse handleInvalidData(final InvalidDataException ex, HttpServletRequest request) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .path(request.getRequestURI())
                .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();
    }

    @ExceptionHandler(value = UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    default ErrorResponse handleInvalidData(final UnauthenticatedException ex, HttpServletRequest request) {
        return ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .detail(ex.getMessage())
                .path(request.getRequestURI())
                .title(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .build();
    }
}
