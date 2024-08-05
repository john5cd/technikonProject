package com.technikon.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyOwnerExistsException extends RuntimeException {
    public PropertyOwnerExistsException(String message) {
        super(message);
    }
}
