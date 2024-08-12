package dev.Abhishek.EcomProductService.controller;

import dev.Abhishek.EcomProductService.dto.ProductRequestDto;
import dev.Abhishek.EcomProductService.dto.ProductResponseDto;
import dev.Abhishek.EcomProductService.exception.InvalidInputException;
import dev.Abhishek.EcomProductService.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    @Qualifier("productService")
    private ProductService productService;

    @GetMapping( )
    public ResponseEntity< List<ProductResponseDto>> getAllProducts(){
        List<ProductResponseDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") UUID id){
        if(id==null)
            throw new InvalidInputException("Input is not correct");
        return ResponseEntity.ok( productService.getProduct(id));
    }

    @PostMapping()
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto){
        ProductResponseDto savedProduct = productService.createProduct(productRequestDto);
         return ResponseEntity.ok(savedProduct);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id")UUID id,@RequestBody ProductRequestDto productRequestDto){
        ProductResponseDto updatedProduct = productService.updateProduct(productRequestDto,id);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable("id")UUID id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
    @GetMapping("/name/{productName}")
    public ResponseEntity<ProductResponseDto> getProductByName(@PathVariable("productName")String productName){
        return ResponseEntity.ok(productService.getProduct(productName));


    }
    @GetMapping("/{min}/{max}")
    public ResponseEntity< List<ProductResponseDto>> getProductByPriceRange(@PathVariable("min")double minPrice,@PathVariable("max")double maxPrice){
        return ResponseEntity.ok(productService.getProducts(minPrice, maxPrice));

    }


    /* @GetMapping("/productexception")
    public ResponseEntity getProductException(){
        throw new RandomException("Exception from product");
    }

     */
}
