package com.example.orderservice.feign;

import com.example.orderservice.dto.order.ReduceQuantityRequestDto;
import com.example.orderservice.dto.product.ProductDetailResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Stream;

/**
 * product service 통신 인터페이스
 */
@FeignClient("product-service")
public interface ProductServiceClient {

    /**
     * product Id에 해당하는 product 조회
     * @param productId 조회할 product 식별자
     * @return productDetail
     */
    @GetMapping("/products/{productId}")
    ProductDetailResponseDto getProduct(@PathVariable Long productId);

    /**
     * order에 사용될 product 수량 감소 요청
     * @param reduceQuantityRequestDtoStream 감소시킬 product 리스트
     */
    @PostMapping("/products/order")
    void order(List<ReduceQuantityRequestDto> reduceQuantityRequestDtoStream);
}
