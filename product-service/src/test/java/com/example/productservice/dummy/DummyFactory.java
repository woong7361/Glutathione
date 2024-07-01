package com.example.productservice.dummy;

import com.example.productservice.Entity.Product;
import com.example.productservice.Entity.ProductType;
import com.example.productservice.dto.product.ProductCreateRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public class DummyFactory {

    public static Product getDummyProduct(Long productId) {
        Product product = Product.builder()
                .productId(productId)
                .name("name")
                .description("desc")
                .unitPrice(100)
                .quantity(20)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .productType(ProductType.builder()
                        .productTypeId(4534L)
                        .type("hat")
                        .build())
                .build();

        product.addStyle("s1");
        product.addStyle("s2");
        product.addStyle("s3");

        return product;
    }

    public static ProductCreateRequestDto getDummyProductCreateRequestDto() {
        ProductCreateRequestDto requestDto = ProductCreateRequestDto.builder()
                .name("name")
                .description("desc")
                .productTypeId(45634L)
                .productStyles(List.of("s1", "s2", "s3"))
                .unitPrice(4563)
                .quantity(13)
                .build();

        return requestDto;
    }
}
