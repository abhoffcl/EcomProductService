package dev.Abhishek.EcomProductService.service.category;

import dev.Abhishek.EcomProductService.dto.CategoryResponseDto;
import dev.Abhishek.EcomProductService.dto.CategoryRequestDto;
import dev.Abhishek.EcomProductService.exception.CategoryNotFoundException;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDto getCategory(UUID categoryId)throws CategoryNotFoundException;
    List<CategoryResponseDto> getAllCategories();
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto, UUID categoryId)throws CategoryNotFoundException;
    boolean deleteCategory(UUID categoryId);
    double getTotalPriceForCategory(UUID categoryId)throws CategoryNotFoundException;
}
