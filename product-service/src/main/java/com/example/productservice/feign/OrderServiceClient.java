package com.example.productservice.feign;

import com.example.productservice.dto.member.MemberDto;
import com.example.productservice.dto.product.ProductTopOrdersDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @GetMapping("/orders/top")
    ProductTopOrdersDto getTopOrderProducts();
}
