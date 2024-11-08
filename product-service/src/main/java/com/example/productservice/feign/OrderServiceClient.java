package com.example.productservice.feign;

import com.example.productservice.dto.member.MemberDto;
import com.example.productservice.dto.product.ProductTopOrdersDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 주문 서비스와의 통신 인터페이스
 */
@FeignClient(name = "order-service")
public interface OrderServiceClient {

    /**
     * 가장 주문이 많은 제품 조회
     * @return 제품 리스트
     */
    @GetMapping("/orders/top")
    List<ProductTopOrdersDto> getTopOrderProducts();
}
