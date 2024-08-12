package dev.Abhishek.EcomProductService.dto;

import dev.Abhishek.EcomProductService.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductResponseDto {
    private int productId;
    private String title;
    private double price;
    private String description;
    private String category;
    private String imageURL;
    private double rating;
}
