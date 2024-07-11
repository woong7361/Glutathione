package com.example.productservice.repository;

import com.example.productservice.Entity.ProductFavorite;
import com.example.productservice.Entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *  제품 이미지 repository
 */
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    /**
     * 제품 식별자로 모든 이미지 삭제
     * @param productId 제품 식별자
     */
    @Modifying
    @Query("UPDATE ProductImage pi " +
            "SET pi.product.productId = null " +
            "WHERE pi.product.productId = :productId")
    void deleteByproductId(Long productId);
}
