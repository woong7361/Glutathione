package com.example.orderservice.dto.cart;

import com.example.orderservice.dto.product.ProductDetailResponseDto;
import lombok.Getter;

@Getter
public class CartResponseDto {
    private Long cartId;
    private ProductDetailResponseDto product;

    private Integer quantity;

    public CartResponseDto(Long cartId, Integer quantity, ProductDetailResponseDto product) {
        this.cartId = cartId;
        this.quantity = quantity;
        this.product = product;
    }
}
