package dev.Abhishek.EcomProductService.controller;

import dev.Abhishek.EcomProductService.client.FakeStoreClient;
import dev.Abhishek.EcomProductService.dto.fakeStoreDto.FakeStoreCartResponseDto;
import dev.Abhishek.EcomProductService.exception.CartNotFoundException;
import dev.Abhishek.EcomProductService.exception.RandomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartController {
    @Autowired
    private FakeStoreClient fakeStoreClient;
    @GetMapping("/cart/{userId}")
    public ResponseEntity getCartForUser(@PathVariable("userId")int userId){
        List<FakeStoreCartResponseDto>cartResponseList  =fakeStoreClient.getCartByUserId(userId);
        if(cartResponseList==null)
            throw new CartNotFoundException("cart for userId = "+ userId +" not found");
        return ResponseEntity.ok(cartResponseList);

    }
    @GetMapping("/cartexception")
    public ResponseEntity getCartException(){
        throw new RandomException("Exception from cart");
    }
}
