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

    /**
     * 제품 좋아요 추가
     * @param productId 제품 식별자
     * @param memberId 회원 식별자
     * @return 제품
     */
    Optional<ProductFavorite> findByProductProductIdAndMemberId(Long productId, Long memberId);

    /**
     * 제품 좋아요 취소
     * @param productId 제품 식별자
     * @param memberId 회원 식별자
     */
    void deleteByProductProductIdAndMemberId(Long productId, Long memberId);
}
