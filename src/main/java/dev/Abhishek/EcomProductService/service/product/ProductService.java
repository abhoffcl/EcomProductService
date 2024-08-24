package dev.Abhishek.EcomProductService.service.product;

import dev.Abhishek.EcomProductService.dto.ProductQuantityDto;
import dev.Abhishek.EcomProductService.dto.ProductRequestDto;
import dev.Abhishek.EcomProductService.dto.ProductResponseDto;
import dev.Abhishek.EcomProductService.exception.CategoryNotFoundException;
import dev.Abhishek.EcomProductService.exception.ProductNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductResponseDto>getAllProducts();
    ProductResponseDto getProduct(UUID productId)throws ProductNotFoundException;
    ProductResponseDto createProduct(ProductRequestDto product)throws CategoryNotFoundException;
    ProductResponseDto updateProduct(ProductRequestDto updateProduct, UUID productId)throws ProductNotFoundException;
    List<ProductResponseDto> setQuantityForProducts(List<ProductQuantityDto>productQuantityDtos)throws ProductNotFoundException;
    boolean deleteProduct(UUID productId);
    ProductResponseDto getProduct(String productName)throws ProductNotFoundException;
    List<ProductResponseDto> getProducts(double minPrice,double maxPrice);


}
