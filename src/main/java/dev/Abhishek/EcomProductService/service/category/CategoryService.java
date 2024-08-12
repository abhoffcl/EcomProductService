package dev.Abhishek.EcomProductService.service.category;

import dev.Abhishek.EcomProductService.dto.CategoryResponseDto;
import dev.Abhishek.EcomProductService.dto.CategoryRequestDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDto getCategory(UUID categoryId);
    List<CategoryResponseDto> getAllCategories();
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto, UUID categoryId);
    boolean deleteCategory(UUID categoryId);
    double getTotalPriceForCategory(UUID categoryId);
}
