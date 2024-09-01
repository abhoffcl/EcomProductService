package dev.Abhishek.EcomProductService.controller;

import dev.Abhishek.EcomProductService.dto.ProductQuantityDto;
import dev.Abhishek.EcomProductService.dto.ProductRequestDto;
import dev.Abhishek.EcomProductService.dto.ProductResponseDto;
import dev.Abhishek.EcomProductService.exception.InvalidInputException;
import dev.Abhishek.EcomProductService.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping( )
    public ResponseEntity< List<ProductResponseDto>> getAllProducts(){
        List<ProductResponseDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") UUID id){
        if(id==null)
            throw new InvalidInputException("Enter valid id");
        return  ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping()
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto){
        ProductResponseDto savedProduct = productService.createProduct(productRequestDto);
         return ResponseEntity.ok(savedProduct);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id")UUID id,@RequestBody ProductRequestDto productRequestDto){
        if(id==null)
            throw new InvalidInputException("Enter valid id");
        ProductResponseDto updatedProduct = productService.updateProduct(productRequestDto,id);
        return ResponseEntity.ok(updatedProduct);
    }
    @PostMapping("/stock")
    public ResponseEntity<List<ProductResponseDto>>setQuantityForProducts(@RequestBody List<ProductQuantityDto>productQuantityDtos){
        return ResponseEntity.ok(productService.setQuantityForProducts(productQuantityDtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable("id")UUID id){
        if(id==null)
            throw new InvalidInputException("Enter valid id");
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
    @GetMapping("/name/{productName}")
    public ResponseEntity<ProductResponseDto> getProductByName(@PathVariable("productName")String productName){
        if(productName==null || productName.isBlank())
            throw new InvalidInputException("Product name is invalid");
        return ResponseEntity.ok(productService.getProduct(productName));

    }
    @GetMapping("/{min}/{max}")
    public ResponseEntity< List<ProductResponseDto>> getProductByPriceRange(@PathVariable("min")double minPrice,@PathVariable("max")double maxPrice){
        if(!(minPrice>0 && maxPrice>0 && minPrice<maxPrice))
            throw new  InvalidInputException("Enter valid price range ");
        return ResponseEntity.ok(productService.getProducts(minPrice, maxPrice));
    }

}
