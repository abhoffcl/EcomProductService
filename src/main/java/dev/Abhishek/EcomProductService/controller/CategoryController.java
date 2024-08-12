package dev.Abhishek.EcomProductService.controller;

import dev.Abhishek.EcomProductService.dto.CategoryResponseDto;
import dev.Abhishek.EcomProductService.dto.CategoryRequestDto;
import dev.Abhishek.EcomProductService.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    @GetMapping("/{id}")
    ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable("id") UUID categoryId){
        return ResponseEntity.ok(categoryService.getCategory(categoryId));

    }
    @PostMapping
    ResponseEntity<CategoryResponseDto>createCategory(@RequestBody CategoryRequestDto categoryRequestDto){
        return ResponseEntity.ok(categoryService.createCategory(categoryRequestDto));

    }
    @PutMapping("{id}")
    ResponseEntity<CategoryResponseDto>updateCategory(@PathVariable("id") UUID categoryId, @RequestBody CategoryRequestDto categoryRequestDto){
        return ResponseEntity.ok(categoryService.updateCategory(categoryRequestDto,categoryId));

    }
    @DeleteMapping("{id}")
    ResponseEntity<Boolean>deleteCategory(@PathVariable("id")UUID categoryId){
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));

    }
    @GetMapping("totalPrice/{id}")
    ResponseEntity<Double> getTotaLPriceForAllProducts(@PathVariable("id") UUID categoryId){
        return ResponseEntity.ok(categoryService.getTotalPriceForCategory(categoryId));

    }

}
