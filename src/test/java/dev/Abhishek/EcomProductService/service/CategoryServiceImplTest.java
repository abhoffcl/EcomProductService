package dev.Abhishek.EcomProductService.service;

import dev.Abhishek.EcomProductService.entity.Category;
import dev.Abhishek.EcomProductService.entity.Product;
import dev.Abhishek.EcomProductService.exception.CategoryNotFoundException;
import dev.Abhishek.EcomProductService.repository.CategoryRepository;
import dev.Abhishek.EcomProductService.service.category.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testGetTotalPriceForAllProductsUnderCategory(){
        //
        UUID categoryId = UUID.randomUUID();
        Optional<Category>categoryOptionalMock = getCategoryMockData();
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(categoryOptionalMock);
        double actualTotalCost = categoryService.getTotalPriceForCategory(categoryId);
        double expectedTotalCost = 300.00;
        Assertions.assertEquals(expectedTotalCost,actualTotalCost);


    }
    @Test
    public void testGetTotalPriceForZeroProductsUnderCategory(){
        //Arrange
        UUID categoryId = UUID.randomUUID();
        Optional<Category>categoryOptionalMock = getCategoryMockDataWithZeroProduct();
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(categoryOptionalMock);
        double expectedCost=0.0;

        //Act
        double actualCost= categoryService.getTotalPriceForCategory(categoryId);

        //Assert
        Assertions.assertEquals(expectedCost,actualCost);
        Mockito.verify(categoryRepository).findById(categoryId);
    }
    @Test
    public void testCategoryNotFoundExceptionThrown(){
        //Arrange
        UUID categoryId = UUID.randomUUID();
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        //Act and Assert
        Assertions.assertThrows(CategoryNotFoundException.class,()-> categoryService.getTotalPriceForCategory(categoryId));
    }
    public Optional<Category> getCategoryMockData(){
        Category category = new Category();
        category.setId( UUID.randomUUID());
        category.setName("CategoryRandom");

        Product product1 = new Product();
        product1.setCategory(category);
        product1.setTitle("product1");
        product1.setPrice(100.00);
        product1.setId(UUID.randomUUID());

        Product product2 = new Product();
        product2.setCategory(category);
        product2.setTitle("product2");
        product2.setPrice(200.00);
        product2.setId(UUID.randomUUID());

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        category.setProducts(products);
        return Optional.of(category);

    }
    public Optional<Category>getCategoryMockDataWithZeroProduct(){
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("CategoryName");
        List<Product>products=new ArrayList<>();
        category.setProducts(products);
        return Optional.of(category);

    }
}
