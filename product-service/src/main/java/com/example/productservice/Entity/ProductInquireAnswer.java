package com.example.productservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 상품 문의 답변
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductInquireAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productInquireAnswerId;

    private String content;

    private Long productId;

    @OneToOne
    @JoinColumn(name = "product_inquire_id")
    private ProductInquire productInquire;


    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
