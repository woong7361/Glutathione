package com.example.productservice.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFavoriteDto {
    private Long productId;
    private Long favoriteCount;
    private Boolean isFavor;
}
