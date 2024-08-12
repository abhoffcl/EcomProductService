package dev.Abhishek.EcomProductService.controller.orderClient;

import dev.Abhishek.EcomProductService.dto.PurchaseProductRequestDto;
import dev.Abhishek.EcomProductService.dto.orderDto.FailedOrderProductsDto;
import dev.Abhishek.EcomProductService.service.orderClient.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/failed")
    public ResponseEntity<Void> handleOrderFailure(@RequestBody List<FailedOrderProductsDto> failedProducts) {
        orderService.handleOrderFailure(failedProducts);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/place-order")
    public ResponseEntity<Void> placeOrder(@RequestBody List<PurchaseProductRequestDto> purchaseProductRequestDtos) {
        orderService.placeOrder(purchaseProductRequestDtos);
        return ResponseEntity.ok().build();
    }
}