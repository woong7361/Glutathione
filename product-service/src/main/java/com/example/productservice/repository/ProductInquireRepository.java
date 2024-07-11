package com.example.productservice.repository;

import com.example.productservice.Entity.ProductInquire;
import com.example.productservice.Entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInquireRepository extends JpaRepository<ProductInquire, Long> {
    /**
     * 문의글 식별자와 회원 식별자로 제품문의 찾기
     * @param inquireId 문의글 식별자
     * @param memberId 회원 식별자
     * @return nullable 문의글
     */
    Optional<ProductInquire> findByProductInquireIdAndMemberId(Long inquireId, Long memberId);

    /**
     * 제품에 대한 모든 문의 조회
     * @param productId 제품 식별자
     * @return 모든 문의
     */
    List<ProductInquire> findByProductProductId(Long productId);

    /**
     * 제품에 대한 모든 문의 삭제
     * @param productId 제품 식별자
     */
    void deleteByProductProductId(Long productId);
}
