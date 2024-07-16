package com.example.productservice.dto.banner;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BannerResponseDto {
    private Long bannerId;
    private String url;
    private Long bannerImageId;
}
