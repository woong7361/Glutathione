package com.example.orderservice.dto.product;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 상품 상세정보 응답 DTO
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDetailResponseDto {
    private Long productId;

    private String name;
    private String description;

    private ProductType productType;
    private List<String> productStyles;
    private Long thumbnailImageId;

    private Integer unitPrice;
    private Integer quantity;

    @Setter
    private Long FavorCount;
    @Setter
    private Boolean isFavor;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ProductType {
        private Long productTypeId;

        private String type;
    }

}
