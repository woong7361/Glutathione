package com.example.productservice.controller;

import com.example.productservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 파일 관련 컨트롤러
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
    @PostMapping("/products/{productId}/images")
    public ResponseEntity<Map<String, Long>> saveFile(@RequestPart MultipartFile multipartFile, @PathVariable Long productId) {
        Long imageId = fileService.uploadFile(multipartFile, productId);

        return ResponseEntity.ok(Map.of(IMAGE_ID_RESPONSE_KEY, imageId));
    }


    @GetMapping("/products/images/{imageId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long imageId) {
        byte[] fileStream = fileService.getFileStream(imageId);
        return ResponseEntity
                .ok()
                .contentLength(fileStream.length)
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileStream);
    }
}
