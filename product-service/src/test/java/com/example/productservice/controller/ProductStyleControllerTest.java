package com.example.productservice.controller;

import com.example.productservice.dto.style.StyleCountDto;
import com.example.productservice.service.ProductStyleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductStyleController.class)
@ActiveProfiles("local")
class ProductStyleControllerTest {
    public static final String GET_MOST_STYLES_URI = "/products/styles/most";

    @MockBean
    ProductStyleService productStyleService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    @DisplayName("가장 많은 스타일 반환")
    public class getMostStyle {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            int size = 10;
            Mockito.when(productStyleService.getMostStyles(size))
                    .thenReturn(List.of(
                            new StyleCountDto() {
                                @Override
                                public String getStyle() {
                                    return "style";
                                }

                                @Override
                                public Long getCount() {
                                    return 5L;
                                }
                            }
                    ));

            //when
            ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get(GET_MOST_STYLES_URI)
                    .param("size", String.valueOf(size)));

            //then
            action
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.styles[0].style").value("style"));
        }
    }

}