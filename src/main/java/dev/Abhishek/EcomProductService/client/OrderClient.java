package dev.Abhishek.EcomProductService.client;

import dev.Abhishek.EcomProductService.dto.orderDto.PlaceOrderRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Component
public class OrderClient {
    //place order api
    private RestTemplateBuilder restTemplateBuilder;
    private String orderServiceBaseUrl;
    private String orderServiceValidatePlaceOrderPath;


    public OrderClient(RestTemplateBuilder restTemplateBuilder, @Value("${orderService.api.base.url}") String orderServiceBaseUrl, @Value("${orderService.api.placeOrder.path}") String orderServiceValidatePlaceOrderPath) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.orderServiceBaseUrl = orderServiceBaseUrl;
        this.orderServiceValidatePlaceOrderPath = orderServiceValidatePlaceOrderPath;
    }
    public boolean placeOrder(PlaceOrderRequestDto placeOrderRequestDto){
        String placeOrderUrl = orderServiceBaseUrl.concat(orderServiceValidatePlaceOrderPath);
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Boolean> orderPlaced =
                restTemplate.postForEntity(placeOrderUrl,placeOrderRequestDto,Boolean.class);
        return orderPlaced.getBody();
    }
}
