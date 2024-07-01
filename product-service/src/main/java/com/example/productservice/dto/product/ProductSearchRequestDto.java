package com.example.productservice.dto.product;

import lombok.*;

/**
 * 제품 검색 요청 DTO
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductSearchRequestDto {
//    카테고리, 타입, 스타일, 키워드(상품명 contain)

    private String keyword;
    private String type;
    private String style;

    private String sortElement;
    private String sortType;

    private Integer page = 1;
    private Integer size = 5;

    public Integer getOffset() {
        return (page - 1) * size;
    }
}
