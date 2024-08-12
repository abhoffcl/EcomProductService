package dev.Abhishek.EcomProductService.controller;

import dev.Abhishek.EcomProductService.dto.CategoryResponseDto;
import dev.Abhishek.EcomProductService.dto.CategoryRequestDto;
import dev.Abhishek.EcomProductService.service.category.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class CategoryControllerTest {
    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateCategorySuccess(){
        //Arrange
        UUID randomId = UUID.randomUUID();
        CategoryRequestDto updateRequestDto = new CategoryRequestDto();
        updateRequestDto.setCategoryName("CategoryName");
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategoryId(randomId);
        categoryResponseDto.setCategoryName("CategoryName");

        Mockito.
                when(categoryService.updateCategory(updateRequestDto,randomId)).
                thenReturn(categoryResponseDto);

        //Act
        ResponseEntity<CategoryResponseDto>categoryResponseEntity = categoryController.
                updateCategory(randomId,updateRequestDto);
        //Assert
        Assertions.assertEquals(categoryResponseDto,categoryResponseEntity.getBody());

    }

}
