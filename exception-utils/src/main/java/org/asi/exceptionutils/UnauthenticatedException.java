package org.asi.exceptionutils;

public class UnauthenticatedException extends RuntimeException{
    public UnauthenticatedException(String message) {
        super(message);
    }
}
