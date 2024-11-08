package com.example.orderservice.service;

import com.example.orderservice.entity.CouponImage;
import com.example.orderservice.error.exception.NotFoundException;
import com.example.orderservice.repository.CouponImageRepository;
import com.example.orderservice.repository.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 파일 관련 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class FileService {
    private final CouponImageRepository couponImageRepository;
    private final FileStorage fileStorage;

    /**
     * 파일 스트림 반환
     * @param imageId 이미지 식별자
     * @return 파일 스트림
     */
    public byte[] getFileStream(Long imageId) {
        CouponImage couponImage = couponImageRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException("image not found", imageId));

        File file = fileStorage.getFile(couponImage.getPath() + couponImage.getPhysicalName());

        try(FileInputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        } catch (FileNotFoundException e) {
            throw new NotFoundException("이미지가 존재하지 않습니다.", imageId);
        } catch (IOException e) {
            throw new IllegalArgumentException("file I/O exception");
        }
    }
}
