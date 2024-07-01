package com.example.productservice.converter;


import com.example.productservice.Entity.Product;
import com.example.productservice.dto.Product.ProductCreateRequestDto;
import com.example.productservice.dto.Product.ProductDetailResponseDto;
import com.example.productservice.dummy.DummyFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ProductConverterTest {

    @DisplayName("제품 생성 DTO to 제품 클래스")
    @Test
    public void createRequestToProduct() throws Exception{
        //given
        ProductCreateRequestDto requestDto = ProductCreateRequestDto.builder()
                .name("name")
                .description("desc")
                .productTypeId(45634L)
                .productStyles(List.of("s1", "s2", "s3"))
                .unitPrice(4563)
                .quantity(13)
                .build();

        //when
        Product product = ProductConverter.fromCreateRequestDto(requestDto);

        //then
        assertThat(product.getName()).isEqualTo(requestDto.getName());
        assertThat(product.getDescription()).isEqualTo(requestDto.getDescription());
        assertThat(product.getProductType().getProductTypeId()).isEqualTo(requestDto.getProductTypeId());
        assertThat(product.getUnitPrice()).isEqualTo(requestDto.getUnitPrice());
        assertThat(product.getQuantity()).isEqualTo(requestDto.getQuantity());
    }


    @DisplayName("제품 to 제품 상세 DTO")
    @Test
    public void productToProductDetailResponseDTO() throws Exception{
        //given
        Product product = DummyFactory.getDummyProduct(10L);

        //when
        ProductDetailResponseDto detailDto = ProductConverter.toProductDetailResponseDto(product);

        //then
        assertThat(detailDto.getName()).isEqualTo(product.getName());
        assertThat(detailDto.getDescription()).isEqualTo(product.getDescription());
        assertThat(detailDto.getProductId()).isEqualTo(product.getProductId());
        assertThat(detailDto.getCreatedAt()).isEqualTo(product.getCreatedAt());
        assertThat(detailDto.getModifiedAt()).isEqualTo(product.getModifiedAt());
        assertThat(detailDto.getQuantity()).isEqualTo(product.getQuantity());
        assertThat(detailDto.getUnitPrice()).isEqualTo(product.getUnitPrice());
        assertThat(detailDto.getProductType()).isEqualTo(product.getProductType());
        assertThat(detailDto.getProductStyles())
                .containsAll(product.getProductStyles().stream().map(productStyle -> productStyle.getStyle()).collect(Collectors.toList()));
    }
}