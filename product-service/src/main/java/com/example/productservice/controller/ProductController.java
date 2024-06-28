package com.example.productservice.controller;


import com.example.productservice.Entity.Product;
import com.example.productservice.Entity.ProductType;
import com.example.productservice.dto.Product.ProductCreateRequestDto;
import com.example.productservice.dto.Product.ProductDetailResponseDto;
import com.example.productservice.dto.type.ProductTypeCreateRequestDto;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 상품 관련 endpoint
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    public static final String PRODUCT_ID_RESPONSE_KEY = "productId";
    public static final String PRODUCT_TYPES_RESPONSE_KEY = "types";
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
     * 제품 상세조회
     *
     * @param productId 제품 식별자
     * @return 200 ok
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDetailResponseDto> getProductDetail(@PathVariable Long productId) {
        ProductDetailResponseDto responseDto = productService.getProductDetail(productId);

        return ResponseEntity.ok(responseDto);
    }

    /**
     * 제품 타입 생성
     * @param productTypeCreateRequestDto 요청 타입
     * @return 200 ok
     */
    @PostMapping("/products/types")
    public ResponseEntity<Object> createProductType(@Valid @RequestBody ProductTypeCreateRequestDto productTypeCreateRequestDto) {

        productService.createProductType(productTypeCreateRequestDto);

        return ResponseEntity.ok().build();
    }


    /**
     * 제품 타입 전체 조회
     * @return 200 ok
     */
    @GetMapping("/products/types")
    public ResponseEntity<Map<String, List<ProductType>>> getProductTypes() {
        List<ProductType> productTypes = productService.getProductTypes();

        return ResponseEntity.ok(Map.of(PRODUCT_TYPES_RESPONSE_KEY, productTypes));
    }



}
