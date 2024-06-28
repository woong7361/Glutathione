package com.example.productservice.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 스타일
 * ex. 시원한 여름 스타일, 선선한 가을에 어울리는, ...
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productStyleId;

    @Column(nullable = false)
    private String style;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * 상품 연관관계 설정
     * @param product 상품
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * 상품 연관관계 설정
     * @param productId 상품 식별자
     */
    public void setProduct(Long productId) {
        this.product = Product.builder()
                .productId(productId)
                .build();
    }
}
