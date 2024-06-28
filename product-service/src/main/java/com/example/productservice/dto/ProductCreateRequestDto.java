package com.example.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 상품 생성 요청 DTO
 */
@Getter
@Builder
@AllArgsConstructor
public class ProductCreateRequestDto {
    @NotNull
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Long productTypeId;

    @NotNull
    private List<String> productStyles;

    @NotNull
    private Integer unitPrice;

    @NotNull
    private Integer quantity;
}
