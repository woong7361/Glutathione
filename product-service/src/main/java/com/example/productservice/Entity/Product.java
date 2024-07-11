package com.example.productservice.Entity;


import com.example.productservice.dto.product.ProductUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;
    private String description;
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductStyle> productStyles = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductImage> productImages = new ArrayList<>();

    private Integer unitPrice;
    private Integer quantity;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    public void addStyle(ProductStyle productStyle) {
        if (this.productStyles == null) {
            this.productStyles = new ArrayList<>();
        }

        this.productStyles.add(productStyle);
        productStyle.setProduct(this);
    }

    public void addStyle(String styleString) {
        if (this.productStyles == null) {
            this.productStyles = new ArrayList<>();
        }

        ProductStyle productStyle = ProductStyle.builder()
                .style(styleString)
                .build();

        this.productStyles.add(productStyle);
        productStyle.setProduct(this);
    }

    public void setProductTypeId(Long id) {
        this.productType = ProductType.builder()
                .productTypeId(id)
                .build();
    }

    public void update(ProductUpdateRequestDto updateRequestDto) {
        productStyles = new ArrayList<>();

        name = updateRequestDto.getName();
        content = updateRequestDto.getContent();
        description = updateRequestDto.getDescription();

        setProductTypeId(updateRequestDto.getProductTypeId());
        updateRequestDto.getProductStyles()
                .forEach((sty) -> addStyle(sty));

        unitPrice = updateRequestDto.getUnitPrice();
        quantity = updateRequestDto.getQuantity();
    }
}
