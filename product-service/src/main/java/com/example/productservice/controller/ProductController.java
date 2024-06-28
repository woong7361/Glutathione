package com.example.productservice.controller;


import com.example.productservice.Entity.Product;
import com.example.productservice.dto.ProductCreateRequestDto;
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

/**
 * 상품 관련 endpoint
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/health-check")
    @Transactional
    public String healthCheck() {
        return "healthy";
    }

    /**
     * 상품 추가(생성)
     */
    @PostMapping("/products")
    public void createProduct(@Valid @RequestBody ProductCreateRequestDto createRequestDto) {
        Product product = productService.createProduct(createRequestDto);

        ResponseEntity.ok(product);
    }

}
