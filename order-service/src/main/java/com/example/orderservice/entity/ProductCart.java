package com.example.orderservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니 제품
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCartId;

    private Long memberId;

    private Long productId;
    private Integer quantity;

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }
}
