package com.example.productservice.repository.dsl;

import com.example.productservice.dto.product.ProductSearchRequestDto;
import com.example.productservice.dto.product.ProductSearchResponseDto;

import java.util.List;

/**
 * Product 동적 쿼리 repository
 */
public interface QueryDslProductRepository {

    /**
     * 제품 검색
     *
     * @param searchRequestDto 제품 검색 요청 파라미터
     * @param memberId         검색 요청한 회원 식별자
     * @return 검색된 제품
     */
    List<ProductSearchResponseDto> search(ProductSearchRequestDto searchRequestDto, Long memberId);

    /**
     * 제품 총 개수 반환
     * @param searchRequestDto 제품 검색 요청 파라미터
     * @return 검색된 제품 개수
     */
    Long searchCount(ProductSearchRequestDto searchRequestDto);

}
