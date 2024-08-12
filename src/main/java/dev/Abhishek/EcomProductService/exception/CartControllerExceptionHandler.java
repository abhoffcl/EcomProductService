package dev.Abhishek.EcomProductService.exception;

import dev.Abhishek.EcomProductService.controller.CartController;
import dev.Abhishek.EcomProductService.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = CartController.class)
public class CartControllerExceptionHandler {
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity handleCartNotFoundException(CartNotFoundException ce){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                ce.getMessage(),
                404
        );
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RandomException.class)
    public ResponseEntity handleCartRandomException(RandomException pe){
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                pe.getMessage(),
                404
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }


}
