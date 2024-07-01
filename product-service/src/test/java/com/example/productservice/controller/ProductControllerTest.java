package com.example.productservice.controller;

import com.example.productservice.Entity.Product;
import com.example.productservice.Entity.ProductType;
import com.example.productservice.converter.ProductConverter;
import com.example.productservice.dto.Product.ProductCreateRequestDto;
import com.example.productservice.dto.Product.ProductDetailResponseDto;
import com.example.productservice.dto.type.ProductTypeCreateRequestDto;
import com.example.productservice.dummy.DummyFactory;
import com.example.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ActiveProfiles("local")
class ProductControllerTest {
    public static final String CREATE_PRODUCT_URI = "/products";
    public static final String GET_PRODUCTS_DETAIL_URI = "/products/{productId}";
    public static final String GET_PRODUCT_TYPES_URI = "/products/types";

    @MockBean
    private ProductService productService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    @DisplayName("상품 추가 컨트롤러 테스트")
    public class createProductTest {
        @DisplayName("정상 추가")
        @Test
        public void success() throws Exception {
            //given
            ProductCreateRequestDto requestDto = ProductCreateRequestDto.builder()
                    .name("name")
                    .description("desc")
                    .productTypeId(1L)
                    .productStyles(List.of("s1, s2, s3"))
                    .unitPrice(100)
                    .quantity(20)
                    .build();

            Mockito.when(productService.createProduct(any()))
                    .thenReturn(DummyFactory.getDummyProduct(10L));

            //when
            ResultActions action = mockMvc.perform(post(CREATE_PRODUCT_URI)
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON));

            //then
            action
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.productId").value(10L));
        }

        @DisplayName("인자 검증 실패")
        @ParameterizedTest
        @MethodSource("validationArguments")
        public void validationFail(String name, String desc, Long typeId,
                                   List<String> styles, Integer unitPrice, Integer quantity) throws Exception{
            //given
            ProductCreateRequestDto requestDto = ProductCreateRequestDto.builder()
                    .name(name)
                    .description(desc)
                    .productTypeId(typeId)
                    .productStyles(styles)
                    .unitPrice(unitPrice)
                    .quantity(quantity)
                    .build();

            //when
            ResultActions action = mockMvc.perform(post(CREATE_PRODUCT_URI)
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON));

            //then
            action
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertThat(result.getResolvedException())
                            .isInstanceOf(MethodArgumentNotValidException.class));
        }

        private static Stream<Arguments> validationArguments() {
            return Stream.of(
                    Arguments.of(null, "desc", 1L, List.of(), 100, 20),
                    Arguments.of("name", null, 1L, List.of(), 100, 20),
                    Arguments.of("name", "desc", null, null, 100, 20),
                    Arguments.of("name", "desc", 1L, List.of(), null, 20),
                    Arguments.of("name", "desc", 1L, List.of(), 100, null)
            );
        }
    }

    @Nested
    @DisplayName("제품 상세조회 컨트롤러 테스트")
    public class GetDetailProductTest {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            ProductDetailResponseDto detailDto = ProductConverter.toProductDetailResponseDto(
                    DummyFactory.getDummyProduct(156341L));

            Mockito.when(productService.getProductDetail(any()))
                    .thenReturn(detailDto);

            //when
            ResultActions action = mockMvc.perform(get(GET_PRODUCTS_DETAIL_URI, 1L));

            //then
            action
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.productId").value(detailDto.getProductId()))
                    .andExpect(jsonPath("$.name").value(detailDto.getName()))
                    .andExpect(jsonPath("$.description").value(detailDto.getDescription()))
                    .andExpect(jsonPath("$.productType.productTypeId").value(detailDto.getProductType().getProductTypeId()))
                    .andExpect(jsonPath("$.productType.type").value(detailDto.getProductType().getType()))
                    .andExpect(jsonPath("$.productStyles").value(detailDto.getProductStyles()))
                    .andExpect(jsonPath("$.unitPrice").value(detailDto.getUnitPrice()))
                    .andExpect(jsonPath("$.quantity").value(detailDto.getQuantity()));
        }
    }

    @Nested
    @DisplayName("제품 타입 생성 컨트롤러 테스트")
    public class CreateProductTypeTest {

        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            ProductTypeCreateRequestDto type = new ProductTypeCreateRequestDto("type");

            //when
            ResultActions action = mockMvc.perform(post(GET_PRODUCT_TYPES_URI)
                    .content(objectMapper.writeValueAsString(type))
                    .contentType(MediaType.APPLICATION_JSON));

            //then
            action
                    .andExpect(status().isOk());
        }

        @DisplayName("타입 인자 검증 테스트")
        @ParameterizedTest
        @NullSource
        public void test(String type) throws Exception{
            //given
            ProductTypeCreateRequestDto requestDto = new ProductTypeCreateRequestDto(type);

            //when
            ResultActions action = mockMvc.perform(post(GET_PRODUCT_TYPES_URI)
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON));

            //then
            action
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertThat(result.getResolvedException())
                            .isInstanceOf(MethodArgumentNotValidException.class));
        }
    }


    @Nested
    @DisplayName("제품 타입 전체 조회 테스트")
    public class GetAllProductType {
        @DisplayName("정상 처리")
        @Test

        public void success() throws Exception {
            //given
            List<ProductType> types = List.of(
                    ProductType.builder().productTypeId(1L).type("t1").build(),
                    ProductType.builder().productTypeId(2L).type("t2").build(),
                    ProductType.builder().productTypeId(3L).type("t3").build()
            );

            Mockito.when(productService.getProductTypes())
                    .thenReturn(types);

            //when
            ResultActions action = mockMvc.perform(get(GET_PRODUCT_TYPES_URI));

            //then
            action
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.types").isArray());
        }
    }
}