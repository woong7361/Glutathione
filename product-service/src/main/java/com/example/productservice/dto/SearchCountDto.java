package com.example.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCountDto {
    private Long productId;
    private Long favoriteCount;
    private Boolean isFavor;
    private Long inquireCount;
}
