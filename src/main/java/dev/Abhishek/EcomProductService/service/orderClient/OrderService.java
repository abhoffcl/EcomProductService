package dev.Abhishek.EcomProductService.service.orderClient;

import dev.Abhishek.EcomProductService.dto.PurchaseProductRequestDto;
import dev.Abhishek.EcomProductService.dto.orderDto.FailedOrderProductsDto;
import dev.Abhishek.EcomProductService.exception.ClientException.OrderServiceException;
import dev.Abhishek.EcomProductService.exception.ProductNotFoundException;
import dev.Abhishek.EcomProductService.exception.ProductOutOfStockException;

import java.util.List;

public interface OrderService {
    void handleOrderFailure(List<FailedOrderProductsDto> failedProducts)throws ProductNotFoundException ;
    void placeOrder(List<PurchaseProductRequestDto> purchaseProductRequestDtos)throws ProductNotFoundException,
            ProductOutOfStockException, OrderServiceException;

}
