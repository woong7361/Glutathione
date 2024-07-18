package com.example.productservice.dto.inquire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InquireCountDto {
    private Long productId;
    private Long inquireCount;
}
