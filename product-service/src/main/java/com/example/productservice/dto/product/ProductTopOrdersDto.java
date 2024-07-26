package com.example.productservice.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductTopOrdersDto {

    private Long memberId;
    private List<TopProducts> products;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopProducts{
        private Long productId;
        private Long count;
    }
}
