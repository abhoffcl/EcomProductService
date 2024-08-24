package dev.Abhishek.EcomProductService.client;

import dev.Abhishek.EcomProductService.dto.orderDto.PlaceOrderRequestDto;
import dev.Abhishek.EcomProductService.exception.ClientException.OrderServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
    public boolean placeOrder(PlaceOrderRequestDto placeOrderRequestDto)throws OrderServiceException{
        String placeOrderUrl = orderServiceBaseUrl.concat(orderServiceValidatePlaceOrderPath);
        RestTemplate restTemplate = restTemplateBuilder.build();
        try {
            ResponseEntity<Boolean> orderPlaced =
                    restTemplate.postForEntity(placeOrderUrl, placeOrderRequestDto, Boolean.class);
            return Boolean.TRUE.equals(orderPlaced.getBody());
        } catch (ResourceAccessException e) {
            throw new OrderServiceException("Failed to place order: Timeout or resource access issue.");
        } catch (RestClientException e) {
            throw new OrderServiceException("Failed to place order: An error occurred during REST call.");
        }
    }

}
