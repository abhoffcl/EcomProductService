package dev.Abhishek.EcomProductService.dto.orderDto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FailedOrderProductsDto {
    private String productId;
    private Integer quantity;
}
