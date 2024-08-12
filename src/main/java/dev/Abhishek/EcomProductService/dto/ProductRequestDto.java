package dev.Abhishek.EcomProductService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductRequestDto {
    private String title;
    private double price;
    private String description;
    private String imageURL;
    private UUID categoryId;
}
