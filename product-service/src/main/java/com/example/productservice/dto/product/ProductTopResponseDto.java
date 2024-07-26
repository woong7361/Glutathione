package com.example.productservice.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductTopResponseDto {
    private Long count;
    private ProductDetailResponseDto product;
}
