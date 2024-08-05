package com.technikon.exception;

public class MissingInputException extends RuntimeException {
    public MissingInputException(String message) {
        super(message);
    }
}
