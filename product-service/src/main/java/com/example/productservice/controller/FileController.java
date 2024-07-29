package com.example.productservice.controller;

import com.example.productservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 파일 관련 엔드포인트
 */
@RestController
@RequiredArgsConstructor
public class FileController {
    public static final String IMAGE_ID_RESPONSE_KEY = "imageId";
    private final FileService fileService;

    /**
     * 제품 이미지 업로드 엔드포인트
     *
     * @param multipartFile 이미지
     * @return 200 ok
     */
    @PostMapping("/products/images")
    public ResponseEntity<Map<String, Long>> saveFile(@RequestPart MultipartFile multipartFile) {
        Long imageId = fileService.uploadFile(multipartFile);

        return ResponseEntity.ok(Map.of(IMAGE_ID_RESPONSE_KEY, imageId));
    }


    /**
     * 이미지 조회
     * @param imageId 이미지 식별자
     * @return 이미지 byte stream
     */
    @GetMapping("/products/images/{imageId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long imageId) {
        byte[] fileStream = fileService.getProductFileStream(imageId);
        return ResponseEntity
                .ok()
                .contentLength(fileStream.length)
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileStream);
    }

    /**
     * 이미지 삭제
     */
    @DeleteMapping("/products/images/{imageId}")
    public ResponseEntity<Object> deleteFile(@PathVariable Long imageId) {
        fileService.deleteImage(imageId);

        return ResponseEntity.ok().build();
    }

    /**
     * 배너 이미지 조회
     * @param imageId 이미지 식별자
     * @return 이미지 byte stream
     */
    @GetMapping("/banners/images/{imageId}")
    public ResponseEntity<byte[]> getBannerImage(@PathVariable Long imageId) {
        byte[] fileStream = fileService.getBannerFileStream(imageId);
        return ResponseEntity
                .ok()
                .contentLength(fileStream.length)
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileStream);
    }

}
