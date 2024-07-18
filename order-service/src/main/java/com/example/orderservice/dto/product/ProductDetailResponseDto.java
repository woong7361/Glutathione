package com.example.orderservice.dto.product;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDetailResponseDto {
    private Long productId;

    private String name;
    private String description;
//    private String content;

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
