package dev.Abhishek.EcomProductService.service.category;

import dev.Abhishek.EcomProductService.dto.CategoryResponseDto;
import dev.Abhishek.EcomProductService.dto.CategoryRequestDto;
import dev.Abhishek.EcomProductService.dto.ProductResponseDto;
import dev.Abhishek.EcomProductService.entity.Category;
import dev.Abhishek.EcomProductService.entity.Product;
import dev.Abhishek.EcomProductService.exception.CategoryNotFoundException;
import dev.Abhishek.EcomProductService.repository.CategoryRepository;
import dev.Abhishek.EcomProductService.service.product.ProductService;
import dev.Abhishek.EcomProductService.service.product.ProductServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }
    @Override
    public CategoryResponseDto getCategory(UUID categoryId)throws CategoryNotFoundException{
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(()->new CategoryNotFoundException("category not found for id :"+categoryId));
        return convertCategoryToCategoryResponseDto(category);
    }
    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<Category>categories=categoryRepository.findAll();
        List<CategoryResponseDto>categoryResponseDtos= !(categories==null || categories.isEmpty())?categories.
                stream().
                map(category ->convertCategoryToCategoryResponseDto(category) ).
                collect(Collectors.toList()) : new ArrayList<>();
        return categoryResponseDtos;
    }
    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
       Category category  =convertCategoryRequestDtoToCategory(categoryRequestDto);
       Category savedCategory = categoryRepository.save(category);
       return convertCategoryToCategoryResponseDto(savedCategory);
    }
    @Override
    public CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto, UUID categoryId)throws CategoryNotFoundException {
        Category savedCategory =categoryRepository.findById(categoryId).
                orElseThrow(()->new CategoryNotFoundException("category not found for id: "+categoryId));
        savedCategory.setName(categoryRequestDto.getCategoryName());
        return convertCategoryToCategoryResponseDto(categoryRepository.save(savedCategory));
    }
    @Override
    public boolean deleteCategory(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
        return true;
    }
    @Override
    public double getTotalPriceForCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException("Category for the given id is not found")
        );
        if(category.getProducts().isEmpty()){
            return 0;
        } else{
            double sum = 0;
            for(Product p : category.getProducts()){
                sum = sum + p.getPrice();
            }
            return sum;
        }
    }
    public  CategoryResponseDto convertCategoryToCategoryResponseDto(Category category){
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategoryId(category.getId());
        categoryResponseDto.setCategoryName(category.getName());
        List<Product>products=category.getProducts();
        List<ProductResponseDto>productResponseDtos= products!=null?
                products.
                        stream().
                        map(product -> ((ProductServiceImpl)productService).convertProductEntityToProductResponseDto(product) ).
                        collect(Collectors.toList()) : new ArrayList<>();
        categoryResponseDto.setProducts(productResponseDtos);
        return categoryResponseDto;
    }
    public  Category convertCategoryRequestDtoToCategory(CategoryRequestDto categoryRequestDto){
        Category category = new Category();
        category.setName(categoryRequestDto.getCategoryName());
        return category;
    }
}
