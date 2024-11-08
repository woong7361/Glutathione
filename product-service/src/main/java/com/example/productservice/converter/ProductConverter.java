package com.example.productservice.converter;

import com.example.productservice.Entity.Product;
import com.example.productservice.dto.product.ProductCreateRequestDto;
import com.example.productservice.dto.product.ProductDetailResponseDto;
import com.example.productservice.dto.product.ProductSearchResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

/**
 * Product 클래스를 반환해주는 Converter Util
 */
public class ProductConverter {

    /**
     * 제품 생성 요청클래스를 제품 클래스로 변환
     * @param requestDto 제품 생성 요청
     * @return Product class
     */
    public static Product fromCreateRequestDto(ProductCreateRequestDto requestDto) {
        Product product = Product.builder()
                .name(requestDto.getName())
                .content(requestDto.getContent())
                .description(requestDto.getDescription())
                .unitPrice(requestDto.getUnitPrice())
                .quantity(requestDto.getQuantity())
                .build();

        product.setProductTypeId(requestDto.getProductTypeId());
        requestDto.getProductStyles()
                .forEach(style -> product.addStyle(style));

        return product;
    }

    /**
     * product to productDetailResponseDto
     * @param product target 제품
     * @return detail response dto
     */
    public static ProductDetailResponseDto toProductDetailResponseDto(Product product) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(STRICT)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        ProductDetailResponseDto result = mapper.map(product, ProductDetailResponseDto.class);
        result.setProductStyles(product.getProductStyles());
        result.setThumbnailImageId(product.getProductImages().get(0));
        return result;
    }

    /**
     * product to productSearchResponseDto
     * @param product 상품
     * @return 상품 검색 response DTO
     */
    public static ProductSearchResponseDto toProductSearchResponseDto(Product product) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(STRICT)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        ProductSearchResponseDto result = mapper.map(product, ProductSearchResponseDto.class);
        result.setProductStyles(product.getProductStyles());
        return result;
    }
}
