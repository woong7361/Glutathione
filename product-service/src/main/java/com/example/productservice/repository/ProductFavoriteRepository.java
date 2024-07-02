package com.example.productservice.repository;

import com.example.productservice.Entity.ProductFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *  제품 좋아요 목록 repository
 */
@Repository
public interface ProductFavoriteRepository extends JpaRepository<ProductFavorite, Long> {
    Optional<ProductFavorite> findByProductProductIdAndMemberId(Long productId, Long memberId);
}
