package com.example.productservice.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImageId;

    private Long productId;
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;

    private String physicalName;
    private String originalName;
    private String path;
}
