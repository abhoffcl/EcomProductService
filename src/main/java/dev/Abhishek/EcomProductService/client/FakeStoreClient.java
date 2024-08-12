package dev.Abhishek.EcomProductService.client;

import dev.Abhishek.EcomProductService.dto.fakeStoreDto.FakeStoreCartResponseDto;
import dev.Abhishek.EcomProductService.dto.fakeStoreDto.FakeStoreProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FakeStoreClient {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Value("${fakestore.api.base.url}")
    private String fakeStoreAPIBaseURL;
    @Value("${fakestore.api.product.path}")
    private String fakeStoreAPIProductPath;
    @Value("${fakestore.api.cart.for.user.path}")
    private String fakeStoreAPICartForUser;

    public List<FakeStoreProductResponseDto> getAllProducts(){
        String fakeStoreGetAllProductURL = fakeStoreAPIBaseURL.concat(fakeStoreAPIProductPath);
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductResponseDto[]>productResponseList =
                restTemplate.getForEntity(fakeStoreGetAllProductURL,FakeStoreProductResponseDto[].class);
        return List.of(productResponseList.getBody());
    }
    public FakeStoreProductResponseDto getProductById(int id){
        String fakeStoreGetProductURL = fakeStoreAPIBaseURL.concat(fakeStoreAPIProductPath).concat("/ "+id);
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductResponseDto>productResponse = restTemplate.getForEntity(fakeStoreGetProductURL,FakeStoreProductResponseDto.class);
        return productResponse.getBody();
    }
    public List<FakeStoreCartResponseDto> getCartByUserId(int userId){
        if(userId<1)return null;

        String fakeStoreGetCartForUserURL = fakeStoreAPIBaseURL.concat(fakeStoreAPICartForUser).concat(String.valueOf(userId));
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreCartResponseDto[]>cartResponseList =
                restTemplate.getForEntity(fakeStoreGetCartForUserURL,FakeStoreCartResponseDto[].class);
        return List.of(cartResponseList.getBody());
    }
}
