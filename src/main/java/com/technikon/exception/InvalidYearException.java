package com.technikon.exception;


public class InvalidYearException extends RuntimeException{
    public InvalidYearException(String message) {
        super(message);
    }
}
