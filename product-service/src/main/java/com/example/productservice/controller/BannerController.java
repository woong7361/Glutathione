package com.example.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 배너 관련 엔드포인트
 */
@RestController
@RequiredArgsConstructor
public class BannerController {

    @PostMapping("/banners")
    public void createBanner(
            @RequestParam MultipartFile multipartFile, @RequestParam String url
    ) {
        return;
    }
}
