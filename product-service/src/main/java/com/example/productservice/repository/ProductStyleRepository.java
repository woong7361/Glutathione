package com.example.productservice.repository;

import com.example.productservice.Entity.ProductStyle;
import com.example.productservice.dto.style.StyleCountDto;
import com.example.productservice.repository.dsl.QueryDslProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 제품 스타일 repository
 */
@Repository
public interface ProductStyleRepository extends JpaRepository<ProductStyle, Long>, QueryDslProductRepository {

    /**
     * 빈도수가 높은 스타일 반환
     * @param size 반환할 크기
     * @return 스타일 리스트
     */
    @Query(value = "SELECT product_style.style as style, COUNT(product_style.product_style_id) as count " +
            "FROM product_style " +
            "GROUP BY product_style.style " +
            "ORDER BY count DESC " +
            "LIMIT :size",
            nativeQuery = true)
    List<StyleCountDto> findMostCommonStyle(Integer size);

    /**
     * 삭제
     * @param productId 제품 식별자
     */
    void deleteByProductProductId(Long productId);
}
