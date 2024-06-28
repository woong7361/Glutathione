package com.example.productservice.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 제품 타입 entity
 * ex. pants, shirt, ...
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductType {
    @Id
    @GeneratedValue
    private Long productTypeId;

    @Column(nullable = false, unique = true)
    private String type;
}
