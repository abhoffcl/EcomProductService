package dev.Abhishek.EcomProductService.service.orderClient;

import dev.Abhishek.EcomProductService.dto.PurchaseProductRequestDto;
import dev.Abhishek.EcomProductService.dto.orderDto.FailedOrderProductsDto;

import java.util.List;

public interface OrderService {
    void handleOrderFailure(List<FailedOrderProductsDto> failedProducts);
    boolean placeOrder(List<PurchaseProductRequestDto> purchaseProductRequestDtos)

}
