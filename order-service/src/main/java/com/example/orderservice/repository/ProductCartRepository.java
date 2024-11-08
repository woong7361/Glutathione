package com.example.orderservice.repository;

import com.example.orderservice.entity.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 장바구니 repository
 */
@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {
    /**
     * 회원 식별자와 상품 식별자로 장바구니 확인
     * @param memberId 회원 식별자
     * @param productId 상품 식별자
     * @return 장바구니 품목
     */
    Optional<ProductCart> findByMemberIdAndProductId(Long memberId, Long productId);

    /**
     * 회원 식별자로 장바구니 확인
     * @param memberId 회원 식별자
     * @return 장바구니 리스트
     */
    List<ProductCart> findByMemberId(Long memberId);

    /**
     * 장바구니 삭제
     * @param productCartId 장바구니 식별자
     * @param memberId 회원 식별자
     */
    void deleteByProductCartIdAndMemberId(Long productCartId, Long memberId);
}
