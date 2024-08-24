package dev.Abhishek.EcomProductService.service.orderClient;

import dev.Abhishek.EcomProductService.client.OrderClient;
import dev.Abhishek.EcomProductService.dto.ProductQuantityDto;
import dev.Abhishek.EcomProductService.dto.PurchaseProductRequestDto;
import dev.Abhishek.EcomProductService.dto.orderDto.FailedOrderProductsDto;
import dev.Abhishek.EcomProductService.dto.orderDto.OrderItemDto;
import dev.Abhishek.EcomProductService.dto.orderDto.PlaceOrderRequestDto;
import dev.Abhishek.EcomProductService.entity.Product;
import dev.Abhishek.EcomProductService.exception.ClientException.OrderServiceException;
import dev.Abhishek.EcomProductService.exception.ProductNotFoundException;
import dev.Abhishek.EcomProductService.exception.ProductOutOfStockException;
import dev.Abhishek.EcomProductService.repository.ProductRepository;
import dev.Abhishek.EcomProductService.service.product.ProductService;
import dev.Abhishek.EcomProductService.service.product.ProductServiceImpl;
import dev.Abhishek.EcomProductService.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderClient orderClient;
    private ProductRepository productRepository;
    private ProductService productService;

    public OrderServiceImpl(OrderClient orderClient, ProductRepository productRepository, ProductService productService) {
        this.orderClient = orderClient;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Override
    public void handleOrderFailure(List<FailedOrderProductsDto> failedProducts)throws ProductNotFoundException {
        ((ProductServiceImpl)productService).updateStockOnOrderFailure(failedProducts);
    }
    @Override
    public void placeOrder(List<PurchaseProductRequestDto> purchaseProductRequestDtos)throws ProductNotFoundException ,
            ProductOutOfStockException, OrderServiceException {
        PlaceOrderRequestDto placeOrderRequestDto =new PlaceOrderRequestDto();
        List<OrderItemDto> items = purchaseProductRequestDtos.stream()
                .map(dto -> {
                    UUID productId = dto.getProductId();
                    Product savedProduct = productRepository.findById(productId)
                            .orElseThrow(() -> new ProductNotFoundException("Product not found for id " + productId));
                    if(savedProduct.getQuantity()< dto.getQuantity())
                        throw new ProductOutOfStockException("Product with id "+productId+ " is not available");
                    OrderItemDto orderItemDto = new OrderItemDto();
                    orderItemDto.setPrice(savedProduct.getPrice());
                    orderItemDto.setProductId(productId.toString());
                    orderItemDto.setQuantity(dto.getQuantity());
                    return orderItemDto;
                })
                .collect(Collectors.toList());
        placeOrderRequestDto.setUserId(SecurityUtil.getCurrentUserId().toString());
        placeOrderRequestDto.setEmail(SecurityUtil.getCurrentUserEmail());
        placeOrderRequestDto.setUserName(SecurityUtil.getCurrentUserName());
        placeOrderRequestDto.setPhoneNumber(SecurityUtil.getCurrentUserPhoneNumber());
        placeOrderRequestDto.setItems(items);

        Boolean result = orderClient.placeOrder(placeOrderRequestDto);
        if(result==true){
            //reduce product quantity
            updateProductQuantitiesAfterOrder(purchaseProductRequestDtos);
        }
    }
    public void updateProductQuantitiesAfterOrder(List<PurchaseProductRequestDto> purchaseProductRequestDtos)throws ProductNotFoundException{
        List<ProductQuantityDto>productQuantityDtos =purchaseProductRequestDtos.
                stream().
                map(dto->{
                    UUID productId=dto.getProductId();
                    Product savedProduct = productRepository.findById(productId)
                            .orElseThrow(() -> new ProductNotFoundException("Product not found for id " + productId));
                    ProductQuantityDto productQuantityDto = new ProductQuantityDto();
                    productQuantityDto.setProductId(dto.getProductId());
                    productQuantityDto.setQuantity(savedProduct.getQuantity()- dto.getQuantity());
                    return productQuantityDto;
                }).collect(Collectors.toList());
        productService.setQuantityForProducts(productQuantityDtos);
    }
}
