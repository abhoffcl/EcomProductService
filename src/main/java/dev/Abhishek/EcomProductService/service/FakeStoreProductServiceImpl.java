package dev.Abhishek.EcomProductService.service;

import dev.Abhishek.EcomProductService.client.FakeStoreClient;
import dev.Abhishek.EcomProductService.dto.fakeStoreDto.FakeStoreProductResponseDto;
import dev.Abhishek.EcomProductService.entity.Product;
import dev.Abhishek.EcomProductService.exception.NoProductPresentException;
import dev.Abhishek.EcomProductService.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductServiceImpl {
    @Autowired
    private FakeStoreClient fakeStoreClient;

    public List<FakeStoreProductResponseDto> getAllProducts()throws NoProductPresentException {
        List<FakeStoreProductResponseDto>fakeStoreProducts  = fakeStoreClient.getAllProducts();
        if(fakeStoreProducts==null)
            throw new NoProductPresentException("No product available");
        return fakeStoreProducts;
    }


    public FakeStoreProductResponseDto getProduct(int productId)throws ProductNotFoundException
    {
        FakeStoreProductResponseDto fakeStoreProduct = fakeStoreClient.getProductById(productId);
        if(fakeStoreProduct == null)
            throw new ProductNotFoundException("Product with id :"+productId+" not available");
        return fakeStoreProduct;
    }


    public Product createProduct(Product product) {
        return null;
    }


    public Product updateProduct(Product updateProduct, int productId) {
        return null;
    }


    public boolean deleteProduct(int productId) {
        return false;
    }
}
