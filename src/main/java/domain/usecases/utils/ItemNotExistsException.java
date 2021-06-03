package domain.usecases.utils;

public class ItemNotExistsException extends RuntimeException{
    public ItemNotExistsException(String message) {
        super(message);
    }
}


