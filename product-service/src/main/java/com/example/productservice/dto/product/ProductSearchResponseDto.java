package com.example.productservice.dto.product;

import com.example.productservice.Entity.ProductImage;
import com.example.productservice.Entity.ProductStyle;
import com.example.productservice.Entity.ProductType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductSearchResponseDto {

    private Long productId;

    private String name;
    private String description;

    private ProductType productType;
    private List<String> productStyles;
    private Long thumbnailImageId;

    private Integer unitPrice;
    private Integer quantity;

    private Long favorCount;
    private Boolean isFavor;

    private Long inquireCount;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public void setThumbnail(ProductImage productImage) {
        thumbnailImageId = productImage.getProductImageId();
    }

    public void setFavorCount(Long favoriteCount) {
        this.favorCount = favoriteCount;
    }

    public void setIsFavor(Boolean isFavor) {
        this.isFavor = isFavor;
    }

    public void setProductStyles(List<ProductStyle> productStyles) {
        this.productStyles = productStyles.stream()
                .map(ProductStyle::getStyle)
                .toList();
    }

    public void setInquireCount(Long inquireCount) {
        this.inquireCount = inquireCount;
    }
}
