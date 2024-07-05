package com.example.productservice.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImageId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String physicalName;
    private String originalName;
    private String path;

    @ColumnDefault(value = "false")
    private boolean isDelete;

    /**
     * 파일 삭제 표시
     */
    public void delete() {
        this.isDelete = true;
    }

    /**
     * 제품 FK 설정
     * @param productId 제품 식별자
     */
    public void setProductId(Long productId) {
        this.product = Product.builder()
                .productId(productId)
                .build();
    }


    public Long getProductId() {
        return this.product == null ? null : this.product.getProductId();
    }
}
