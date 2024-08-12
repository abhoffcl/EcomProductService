package dev.Abhishek.EcomProductService.exception;

public class NoProductPresentException extends ProductPresentException{
    public NoProductPresentException(String message) {
        super(message);
    }
}
