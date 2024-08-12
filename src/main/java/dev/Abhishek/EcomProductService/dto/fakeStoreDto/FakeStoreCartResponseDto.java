package dev.Abhishek.EcomProductService.dto.fakeStoreDto;

import dev.Abhishek.EcomProductService.dto.ProductQuantityDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FakeStoreCartResponseDto {
    private int id;
    private int userId;
    private String date;
    private List<ProductQuantityDto> products;
    private int __v;

}

