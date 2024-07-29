package com.example.productservice.controller;

import com.example.productservice.service.InquireService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.buf.UEncoder;
import org.aspectj.weaver.patterns.IToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InquireController.class)
@ActiveProfiles("local")
class InquireControllerTest {

    public static final String CREATE_INQUIRE_URI = "/products/{productId}/inquires";
    public static final String AUTHORIZATION = "Authorization";
    public static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MjA3MTA4MDMsImlhdCI6MTcyMDcwNDgwMywic3ViIjoiMSJ9.jdL22jDvyRXFGL5Hfom-4WQv3Chtt5Hf4zUgViQa52ouo-myLagMUwsfIYWojSnw4UGRF9ahQxOKS6WJoAYmCw";

    @MockBean
    InquireService inquireService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    @DisplayName("제품 문의 작성 엔드포인트")
    public class CreateInquireTest {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            Long inquireId = 413L;

            //when
            ResultActions action = mockMvc.perform(post(CREATE_INQUIRE_URI, inquireId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"content\" : \"content\"}")
                    .header(AUTHORIZATION, TOKEN));

            //then
            action.
                    andExpect(status().isOk());
        }
    }
}