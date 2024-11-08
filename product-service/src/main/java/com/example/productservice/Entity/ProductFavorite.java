package com.example.productservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 좋아요
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productFavoriteId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Long memberId;

    public ProductFavorite(Long productId, Long memberId) {
        this.memberId = memberId;
        this.product = Product.builder().productId(productId).build();
    }
}
