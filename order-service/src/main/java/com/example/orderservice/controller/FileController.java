package com.example.orderservice.controller;

import com.example.orderservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    /**
     * 이미지 조회
     * @param imageId 이미지 식별자
     * @return 이미지 byte stream
     */
    @GetMapping("/coupon/images/{imageId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long imageId) {
        byte[] fileStream = fileService.getFileStream(imageId);

        return ResponseEntity
                .ok()
                .contentLength(fileStream.length)
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileStream);
    }
}
