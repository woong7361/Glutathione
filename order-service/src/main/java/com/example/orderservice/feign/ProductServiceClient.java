package com.example.orderservice.feign;

import com.example.orderservice.dto.order.ReduceQuantityRequestDto;
import com.example.orderservice.dto.product.ProductDetailResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Stream;

@FeignClient("product-service")
public interface ProductServiceClient {

    @GetMapping("/products/{productId}")
    ProductDetailResponseDto getProduct(@PathVariable Long productId);

    @PostMapping("/products/order")
    void order(List<ReduceQuantityRequestDto> reduceQuantityRequestDtoStream);
}
