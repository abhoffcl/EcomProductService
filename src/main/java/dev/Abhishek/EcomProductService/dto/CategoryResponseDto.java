package dev.Abhishek.EcomProductService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CategoryResponseDto {
    private UUID categoryId;
    private String categoryName;
    private List<ProductResponseDto> products;
}
