package dev.Abhishek.EcomProductService.service.product;

import dev.Abhishek.EcomProductService.dto.ProductQuantityDto;
import dev.Abhishek.EcomProductService.dto.ProductRequestDto;
import dev.Abhishek.EcomProductService.dto.ProductResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductResponseDto>getAllProducts();
    ProductResponseDto getProduct(UUID productId);
    ProductResponseDto createProduct(ProductRequestDto product);
    ProductResponseDto updateProduct(ProductRequestDto updateProduct, UUID productId);
    List<ProductResponseDto> setQuantityForProducts(List<ProductQuantityDto>productQuantityDtos);
    boolean deleteProduct(UUID productId);
    ProductResponseDto getProduct(String productName);
    List<ProductResponseDto> getProducts(double minPrice,double maxPrice);


}
