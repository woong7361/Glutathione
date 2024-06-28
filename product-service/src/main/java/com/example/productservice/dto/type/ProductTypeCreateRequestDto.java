package com.example.productservice.dto.type;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * product type 생성 요청 dto
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductTypeCreateRequestDto {

    @NotNull
    private String productType;
}
