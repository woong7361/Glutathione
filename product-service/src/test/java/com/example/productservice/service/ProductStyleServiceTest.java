package com.example.productservice.service;

import com.example.productservice.dto.style.StyleCountDto;
import com.example.productservice.repository.ProductStyleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;


class ProductStyleServiceTest {

    ProductStyleRepository productStyleRepository = Mockito.mock(ProductStyleRepository.class);

    ProductStyleService productStyleService = new ProductStyleService(productStyleRepository);

    @Nested
    @DisplayName("가장 많은 스타일 반환 서비스")
    public class NestedClass {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            Integer size = 0;
            Mockito.when(productStyleRepository.findMostCommonStyle(size))
                    .thenReturn(List.of());

            //when
            List<StyleCountDto> mostStyles = productStyleService.getMostStyles(size);

            //then
            Assertions.assertThat(mostStyles.size()).isEqualTo(size);
        }
    }
}