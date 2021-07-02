package org.example.domain.usecases.utils;

public class EntityNotExistsException extends RuntimeException{
    public EntityNotExistsException(String message) {
        super(message);
    }
}


