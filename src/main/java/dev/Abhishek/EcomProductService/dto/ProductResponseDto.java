package dev.Abhishek.EcomProductService.dto;

import dev.Abhishek.EcomProductService.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ProductResponseDto {
    private UUID productId;
    private String title;
    private double price;
    private String description;
    private String category;
    private String imageURL;
    private double rating;
    private int quantity;
}
