package dev.Abhishek.EcomProductService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PurchaseProductRequestDto {
    private UUID productId;
    private int quantity;
}
