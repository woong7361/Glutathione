package com.example.productservice.controller;


import com.example.productservice.Entity.Product;
import com.example.productservice.dto.Product.ProductCreateRequestDto;
import com.example.productservice.dto.type.ProductTypeCreateRequestDto;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 상품 관련 endpoint
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    public static final String PRODUCT_ID_RESPONSE_KEY = "productId";
    private final ProductService productService;

    @GetMapping("/health-check")
    @Transactional
    public String healthCheck() {
        return "healthy";
    }

    /**
     * 상품 추가(생성)
     *
     * @param createRequestDto 요청 상품
     * @return 200 ok
     */
    @PostMapping("/products")
    public ResponseEntity<Map<String, Long>> createProduct(@Valid @RequestBody ProductCreateRequestDto createRequestDto) {
        Product product = productService.createProduct(createRequestDto);

        return ResponseEntity
                .ok(Map.of(PRODUCT_ID_RESPONSE_KEY, product.getProductId()));
    }


    /**
     * 제품 타입 생성
     * @param productTypeCreateRequestDto 요청 타입
     * @return 200 ok
     */
    @PostMapping("/products/type")
    public ResponseEntity<Object> createProductType(@Valid @RequestBody ProductTypeCreateRequestDto productTypeCreateRequestDto) {
        productService.createProductType(productTypeCreateRequestDto);

        return ResponseEntity.ok().build();
    }


}
