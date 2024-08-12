package dev.Abhishek.EcomProductService.dto.orderDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PlaceOrderRequestDto {
    private String userId;
    private String userName;
    private String email;
    private String phoneNumber;
    private List<OrderItemDto> items;

}