package com.example.productservice.dto.product;

import com.example.productservice.Entity.ProductType;

import java.util.ArrayList;
import java.util.List;

public interface FavoriteProductResponseDto {
    Long getProductId();

    String getName();
    String getDescription();

    ProductType getProductType();

    String getProductStylesString();
    Long getThumbnailImageId();

    Integer getUnitPrice();

    Integer getQuantity();

    Long getFavorCount();
    Boolean getIsFavor();

    default List<String> getProductStyles() {
        String styles = getProductStylesString();
        return styles != null ? List.of(styles.split(",")) : new ArrayList<>();
    }
}
