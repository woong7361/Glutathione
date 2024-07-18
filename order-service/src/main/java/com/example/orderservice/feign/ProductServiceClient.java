package com.example.orderservice.feign;

import com.example.orderservice.dto.product.ProductDetailResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("product-service")
public interface ProductServiceClient {

    @GetMapping("/products/{productId}")
    ProductDetailResponseDto getProduct(@PathVariable Long productId);
}
