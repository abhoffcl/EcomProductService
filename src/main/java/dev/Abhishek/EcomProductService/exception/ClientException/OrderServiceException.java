package dev.Abhishek.EcomProductService.exception.ClientException;

public class OrderServiceException extends RuntimeException{
    public OrderServiceException(String message) {
        super(message);
    }
}
