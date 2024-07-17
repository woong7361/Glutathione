package com.example.productservice.controller;

import com.example.productservice.dto.banner.BannerResponseDto;
import com.example.productservice.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 배너 관련 엔드포인트
 */
@RestController
@RequiredArgsConstructor
public class BannerController {
    public static final String BANNER_ID_RESPONSE_KEY = "bannerId";
    private final BannerService bannerService;

    /**
     * banner 생성
     *
     * @param multipartFile 배너 이미지
     * @param url           배너 url
     * @return 배너 id
     */
    @PostMapping("/banners")
    public ResponseEntity<Map<String, Long>> createBanner(
            @RequestParam MultipartFile multipartFile, @RequestParam String url) {
        Long bannerId = bannerService.createBanner(multipartFile, url);

        return ResponseEntity.ok(Map.of(BANNER_ID_RESPONSE_KEY, bannerId));
    }

    /**
     * 배너 전체 조회
     *
     * @return 배너 리스트
     */
    @GetMapping("/banners")
    public ResponseEntity<Map<String, List<BannerResponseDto>>> getBanners() {
        List<BannerResponseDto> banners = bannerService.getAllBanners();

        return ResponseEntity.ok(Map.of("banners", banners));
    }

    /**
     * 배너 삭제
     *
     * @param bannerId 배너 식별자
     * @return 200 ok
     */
    @DeleteMapping("/banners/{bannerId}")
    public ResponseEntity<Object> deleteBanner(@PathVariable Long bannerId) {
        bannerService.deleteBanner(bannerId);

        return ResponseEntity.ok().build();
    }

    /**
     * 배너 수정
     * @param bannerId 배너 식별자
     * @param multipartFile 배너 이미지
     * @param url 배너 url
     * @return 200 ok
     */
    @PutMapping("/banners/{bannerId}")
    public ResponseEntity<Object> updateBanner(
            @PathVariable Long bannerId, @RequestParam MultipartFile multipartFile, @RequestParam String url) {
        bannerService.updateBanner(bannerId, multipartFile, url);

        return ResponseEntity.ok().build();
    }
}
