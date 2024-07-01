package com.example.productservice.repository.dsl;

import com.example.productservice.Entity.Product;
import com.example.productservice.dto.product.ProductSearchRequestDto;

import java.util.List;

/**
 * Product 동적 쿼리 repository
 */
public interface QueryDslProductRepository {
    public List<Product> findProducts();

    /**
     * 제품 검색
     * @param searchRequestDto 제품 검색 요청 파라미터
     * @return 검색된 제품
     */
    List<Product> search(ProductSearchRequestDto searchRequestDto);
}
