package dev.Abhishek.EcomProductService.service.product;

import dev.Abhishek.EcomProductService.dto.ProductRequestDto;
import dev.Abhishek.EcomProductService.dto.ProductResponseDto;
import dev.Abhishek.EcomProductService.dto.orderDto.FailedOrderProductsDto;
import dev.Abhishek.EcomProductService.entity.Category;
import dev.Abhishek.EcomProductService.entity.Product;
import dev.Abhishek.EcomProductService.exception.CategoryNotFoundException;
import dev.Abhishek.EcomProductService.exception.ProductNotFoundException;
import dev.Abhishek.EcomProductService.repository.CategoryRepository;
import dev.Abhishek.EcomProductService.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product>savedProducts=productRepository.findAll();
        List<ProductResponseDto>productResponseDtos =!(savedProducts==null || savedProducts.isEmpty())?savedProducts.
                stream().
                map(savedProduct->convertProductEntityToProductResponseDto(savedProduct)).
                collect(Collectors.toList()) :  new ArrayList<>();
        return productResponseDtos ;
    }
    @Override
    public ProductResponseDto getProduct(UUID productId) {
        Product product= productRepository.findById(productId).
                orElseThrow(()->new ProductNotFoundException("Product not found for id :"+productId));
        return convertProductEntityToProductResponseDto(product);
    }
    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = convertProductRequestDtoToProduct(productRequestDto);
        Category category = categoryRepository.findById(productRequestDto.getCategoryId()).
                orElseThrow(()-> new CategoryNotFoundException("Category not found for id:"+productRequestDto.getCategoryId()));
        product.setCategory(category);
        product= productRepository.save(product);
        return convertProductEntityToProductResponseDto(product);
    }
    @Override
    public ProductResponseDto updateProduct(ProductRequestDto productRequestDto, UUID productId) {
       Product savedProduct = productRepository.findById(productId).
               orElseThrow(()->new ProductNotFoundException("Product not found for id :"+productId));
       savedProduct.setTitle(productRequestDto.getTitle());
       savedProduct.setDescription(productRequestDto.getDescription());
       savedProduct.setImageURL(productRequestDto.getImageURL());
       savedProduct.setPrice(productRequestDto.getPrice());
       Product updatedProduct= productRepository.save(savedProduct);
       return convertProductEntityToProductResponseDto(updatedProduct);
    }
    @Override
    public boolean deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
        return true;
    }
    @Override
    public ProductResponseDto getProduct(String productName) {
        Product savedProduct = productRepository.findProductByTitle(productName);
        if(savedProduct==null)
            throw new ProductNotFoundException("Product :"+productName+"not found" );
        return convertProductEntityToProductResponseDto(savedProduct);
    }
    @Override
    public List<ProductResponseDto> getProducts(double minPrice, double maxPrice) {
        List<Product>products= productRepository.findByPriceBetween(minPrice, maxPrice);
        List<ProductResponseDto>productResponseDtos =!(products==null || products.isEmpty())?products.
                stream().
                map(savedProduct->convertProductEntityToProductResponseDto(savedProduct)).
                collect(Collectors.toList()) :  new ArrayList<>();
        return productResponseDtos;
    }
    public void updateStockOnOrderFailure(List<FailedOrderProductsDto> failedProducts) {
        for(FailedOrderProductsDto failedProduct : failedProducts){
            UUID productId = UUID.fromString(failedProduct.getProductId());
            Product savedProduct = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found for id " + productId));
            savedProduct.setQuantity(savedProduct.getQuantity() + failedProduct.getQuantity());
            productRepository.save(savedProduct);
        }
    }
    public  ProductResponseDto convertProductEntityToProductResponseDto(Product product){
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setRating(product.getRating());
        productResponseDto.setImageURL(product.getImageURL());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setCategory(product.getCategory().getName());
        return productResponseDto;
    }
    public  Product convertProductRequestDtoToProduct(ProductRequestDto productRequestDto){
        Product product = new Product();
        UUID categoryId = productRequestDto.getCategoryId();
        Category productCategory = categoryRepository.findById(categoryId).
                orElseThrow(()->new CategoryNotFoundException("category not found for id :"+categoryId));
        product.setRating(0);
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setTitle(productRequestDto.getTitle());
        product.setImageURL(productRequestDto.getImageURL());
        product.setCategory(productCategory);
        return product;
    }
}
