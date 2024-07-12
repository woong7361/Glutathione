package com.example.productservice.service;

import com.example.productservice.Entity.ProductImage;
import com.example.productservice.dto.file.FileSaveResultDto;
import com.example.productservice.dummy.DummyFactory;
import com.example.productservice.error.exception.NotFoundException;
import com.example.productservice.repository.FileStorage;
import com.example.productservice.repository.ProductImageRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

import static com.example.productservice.dummy.DummyFactory.getDummyProductImage;


class FileServiceTest {

    FileStorage fileStorage = Mockito.mock(FileStorage.class);
    ProductImageRepository productImageRepository = Mockito.mock(ProductImageRepository.class);

    FileService fileService = new FileService(fileStorage, productImageRepository);

    @Nested
    @DisplayName("이미지 업로드 테스트")
    public class ImageUploadTest {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            String originalName = "original_name";
            byte[] bytes = "content".getBytes();
            MockMultipartFile multipartFile = new MockMultipartFile("multitpartFile",originalName, "jpeg", bytes);

            FileSaveResultDto resultDto = FileSaveResultDto.builder()
                    .physicalName("new_name")
                    .path("new_path")
                    .build();

            Mockito.when(fileStorage.save(bytes, originalName))
                    .thenReturn(resultDto);

            //when
            //then
            fileService.uploadFile(multipartFile);
        }
    }

    @Nested
    @DisplayName("이미지 삭제 테스트 ")
    public class ImageDeleteTest {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            Long imageId = 4531L;
            Mockito.when(productImageRepository.findById(imageId))
                    .thenReturn(Optional.of(getDummyProductImage()));
            //when
            //then
            fileService.deleteImage(imageId);
        }

        @DisplayName("해당하는 이미지가 없을때")
        @Test
        public void notExistImage() throws Exception{
            //given
            Long imageId = 4531L;
            Mockito.when(productImageRepository.findById(imageId))
                    .thenReturn(Optional.empty());
            //when
            //then
            Assertions.assertThatThrownBy(() -> fileService.deleteImage(imageId))
                    .isInstanceOf(NotFoundException.class);
        }
    }

}