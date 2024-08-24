package dev.Abhishek.EcomProductService.exception;

import dev.Abhishek.EcomProductService.dto.ExceptionResponseDto;
import dev.Abhishek.EcomProductService.exception.ClientException.OrderServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class GlobalExceptionHandler {
    @ExceptionHandler({ProductPresentException.class})
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

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity handleCategoryNotFoundException(CategoryNotFoundException ce){
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                ce.getMessage(),
                404
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderServiceException.class)
    public ResponseEntity handleOrderServiceExceptionException(OrderServiceException ex){
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                ex.getMessage(),
                503
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity handleProductOutOfStockException(ProductOutOfStockException ce){
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                ce.getMessage(),
                404
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity handleUnauthorizedException(UnauthorizedException ue){
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                ue.getMessage(),
                401
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.UNAUTHORIZED);
    }
}


