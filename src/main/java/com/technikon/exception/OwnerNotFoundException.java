package com.technikon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class OwnerNotFoundException extends RuntimeException {
    private String message;

    public OwnerNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
