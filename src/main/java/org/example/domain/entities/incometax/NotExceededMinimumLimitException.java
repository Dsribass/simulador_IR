package org.example.domain.entities.incometax;

public class NotExceededMinimumLimitException extends RuntimeException{
    public NotExceededMinimumLimitException(String message) {
        super(message);
    }
}
