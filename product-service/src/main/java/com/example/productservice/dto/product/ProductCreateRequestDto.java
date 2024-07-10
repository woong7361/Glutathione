package com.example.productservice.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 상품 생성 요청 DTO
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequestDto {
    @NotNull
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String content;
    @NotNull
    private Long productTypeId;
    @NotNull
    private List<String> productStyles;
    @NotNull
    private Integer unitPrice;
    @NotNull
    private Integer quantity;


    private List<Long> contentImageIds;

    @NotNull
    private Long thumbnailImageId;


    public List<Long> getContentImageIds() {
        return contentImageIds == null ? List.of() : contentImageIds;
    }
}

