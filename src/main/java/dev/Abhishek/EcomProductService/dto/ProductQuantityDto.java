package dev.Abhishek.EcomProductService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductQuantityDto {
    private UUID productId;
    private int quantity;

}
