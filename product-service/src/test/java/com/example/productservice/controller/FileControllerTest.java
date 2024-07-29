package com.example.productservice.controller;


import com.example.productservice.service.FileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
@ActiveProfiles("local")
class FileControllerTest {

    public static final String UPLOAD_IMAGE_URI = "/products/images";
    @MockBean
    FileService fileService;

    @Autowired
    MockMvc mockMvc;

    @Nested
    @DisplayName("파일 업로드 엔드포인트 테스트")
    public class FileUploadTest {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            MockMultipartFile multipartFile = new MockMultipartFile("multipartFile", "content".getBytes());
            Long imageId = 453L;

            Mockito.when(fileService.uploadFile(multipartFile))
                    .thenReturn(imageId);

            //when
            ResultActions action = mockMvc.perform(multipart(UPLOAD_IMAGE_URI)
                    .file(multipartFile));

            //then
            action
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.imageId").value(imageId));
        }
    }

    @Nested
    @DisplayName("파일 삭제 엔드포인트 테스트")
    public class FileDeleteTest {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            long imageId = 48531L;

            //when
            ResultActions action = mockMvc.perform(delete("/products/images/{imageId}", imageId));

            //then
            action
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("파일 조회 엔드포인트 테스트")
    public class FileGetTest {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            Long imageId = 54354L;
            byte[] stream = "content".getBytes();
            Mockito.when(fileService.getProductFileStream(imageId))
                    .thenReturn(stream);

            //when
            ResultActions action = mockMvc.perform(get("/products/images/{imageId}", imageId));

            //then
            action
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.content().bytes(stream));
        }
    }
}