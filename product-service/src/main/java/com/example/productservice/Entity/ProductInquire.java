package com.example.productservice.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductInquire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productInquireId;

    private String content;

    @OneToOne(mappedBy = "productInquire", cascade = CascadeType.REMOVE)
    private ProductInquireAnswer productInquireAnswer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Long memberId;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    public void setProductId(Long productId) {
        this.product = Product.builder()
                .productId(productId)
                .build();
    }
}
