package dev.Abhishek.EcomProductService.exception;

import dev.Abhishek.EcomProductService.controller.ProductController;
import dev.Abhishek.EcomProductService.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = ProductController.class)
public class ProductControllerExceptionHandler {
    @ExceptionHandler({ProductNotFoundException.class,NoProductPresentException.class})
    public ResponseEntity handleProductNotFoundException(ProductPresentException pe){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                pe.getMessage(),
                404
        );
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(InvalidInputException.class)
        public ResponseEntity handleInvalidInputException(InvalidInputException ie){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                ie.getMessage(),
                400
        );
        return new ResponseEntity<>(exceptionResponseDto,HttpStatus.BAD_REQUEST);

        }
    @ExceptionHandler(RandomException.class)
    public ResponseEntity handleProductRandomException(RandomException pe){
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                pe.getMessage(),
                404
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }
}


