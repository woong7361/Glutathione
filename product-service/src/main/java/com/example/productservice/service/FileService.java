package com.example.productservice.service;

import com.example.productservice.Entity.ProductImage;
import com.example.productservice.dto.file.FileSaveResultDto;
import com.example.productservice.error.exception.NotFoundException;
import com.example.productservice.repository.FileStorage;
import com.example.productservice.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final FileStorage fileStorage;
    private final ProductImageRepository productImageRepository;

    /**
     * 제품 이미지 업로드
     * @param multipartFile 제품 이미지
     * @param productId 제품 식별자
     * @return 이미지 식별자
     */
    public Long uploadFile(MultipartFile multipartFile, Long productId) {
        FileSaveResultDto result = fileStorage.save(getBytes(multipartFile), multipartFile.getOriginalFilename());

        ProductImage productImage = ProductImage.builder()
                .path(result.getPath())
                .originalName(multipartFile.getOriginalFilename())
                .physicalName(result.getPhysicalName())
                .productId(productId)
                .build();

        productImageRepository.save(productImage);

        return productImage.getProductImageId();
    }

    /**
     * 제품 이미지 조회
     *
     * @param imageId 이미지 식별자
     * @return input stream
     */
    public byte[] getFileStream(Long imageId) {
        ProductImage productImage = productImageRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException("이미지가 존재하지 않습니다.", imageId));

        File file = fileStorage.getFile(productImage.getPath() + productImage.getPhysicalName());

        try(FileInputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        } catch (FileNotFoundException e) {
            throw new NotFoundException("이미지가 존재하지 않습니다.", imageId);
        } catch (IOException e) {
            //TODO custom exception 필요
            throw new IllegalArgumentException("file I/O exception");
        }
    }


    private byte[] getBytes(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("file I/O exception");
        }
    }
}