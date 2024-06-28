package com.example.productservice.converter;

import com.example.productservice.Entity.Product;
import com.example.productservice.dto.ProductCreateRequestDto;

/**
 * Product 클래스를 반환해주는 Converter Util
 */
public class ProductConverter {

    /**
     * 제품 생성 요청클래스를 제품 클래스로 변환
     * @param requestDto 제품 생성 요청
     * @return Product class
     */
    public static Product createConverter(ProductCreateRequestDto requestDto) {
        Product product = Product.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .unitPrice(requestDto.getUnitPrice())
                .quantity(requestDto.getQuantity())
                .build();

        product.setProductTypeId(requestDto.getProductTypeId());
        requestDto.getProductStyles()
                .forEach(style -> product.addStyle(style));

        return product;
    }
}
