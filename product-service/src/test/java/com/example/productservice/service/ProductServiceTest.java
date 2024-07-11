package com.example.productservice.service;

import com.example.productservice.Entity.Product;
import com.example.productservice.Entity.ProductFavorite;
import com.example.productservice.dto.product.ProductCreateRequestDto;
import com.example.productservice.dto.product.ProductDetailResponseDto;
import com.example.productservice.dto.product.ProductFavoriteDto;
import com.example.productservice.dto.type.ProductTypeCreateRequestDto;
import com.example.productservice.dummy.DummyFactory;
import com.example.productservice.error.exception.DuplicateException;
import com.example.productservice.error.exception.NotFoundException;
import com.example.productservice.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;


class ProductServiceTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ProductTypeRepository productTypeRepository = Mockito.mock(ProductTypeRepository.class);
    ProductFavoriteRepository productFavoriteRepository = Mockito.mock(ProductFavoriteRepository.class);
    ProductImageRepository productImageRepository = Mockito.mock(ProductImageRepository.class);
    ProductStyleRepository productStyleRepository = Mockito.mock(ProductStyleRepository.class);
    ProductInquireRepository productInquireRepository = Mockito.mock(ProductInquireRepository.class);

    ProductService productService = new ProductService(
            productRepository, productTypeRepository, productFavoriteRepository, productImageRepository,
            productStyleRepository, productInquireRepository
    );

    @Nested
    @DisplayName("제품 생성 서비스 테스트")
    public class CreateProductTest {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            ProductCreateRequestDto requestDto = DummyFactory.getDummyProductCreateRequestDto();

            //when
            productService.createProduct(requestDto);
        }
    }

    @Nested
    @DisplayName("제품 타입 생성 서비스 테스트")
    public class CreateProductTypeTest {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            ProductTypeCreateRequestDto type = new ProductTypeCreateRequestDto("type");

            //when
            //then
            productService.createProductType(type);

        }
    }

    @Nested
    @DisplayName("제품 상세조회 서비스 테스트")
    public class GetProductDetailTest {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            Product product = DummyFactory.getDummyProduct(1L);
            ProductFavoriteDto productFavoriteDto = new ProductFavoriteDto(product.getProductId(), 100L, false);

            Mockito.when(productRepository.findByIdWithThumbnail(any(), any()))
                    .thenReturn(Optional.of(product));
            Mockito.when(productRepository.findFavoriteCountById(any(), any()))
                    .thenReturn(productFavoriteDto);

            //when
            ProductDetailResponseDto responseDto = productService.getProductDetail(1L);

            //then
            Assertions.assertThat(responseDto.getName()).isEqualTo(product.getName());
            Assertions.assertThat(responseDto.getDescription()).isEqualTo(product.getDescription());
            Assertions.assertThat(responseDto.getProductId()).isEqualTo(product.getProductId());
            Assertions.assertThat(responseDto.getUnitPrice()).isEqualTo(product.getUnitPrice());
            Assertions.assertThat(responseDto.getQuantity()).isEqualTo(product.getQuantity());
            Assertions.assertThat(responseDto.getCreatedAt()).isEqualTo(product.getCreatedAt());
            Assertions.assertThat(responseDto.getModifiedAt()).isEqualTo(product.getModifiedAt());
            Assertions.assertThat(responseDto.getProductType()).isEqualTo(product.getProductType());
            Assertions.assertThat(responseDto.getProductStyles()).containsAll(product.getProductStyles().stream().map(s -> s.getStyle()).collect(Collectors.toList()));
        }

        @DisplayName("식별자에 해당하는 제품이 없을때")
        @Test
        public void notFound() throws Exception{
            //given
            Mockito.when(productRepository.findById(any()))
                    .thenReturn(Optional.empty());
            //when
            //then
            assertThatThrownBy(() -> productService.getProductDetail(1L))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("제품 타입 전체 조회 서비스 테스트")
    public class findAllType {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            //when
            //then
            productService.getProductTypes();
        }
    }

    @Nested
    @DisplayName("제품 좋아요 추가")
    public class createProductFavorite {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            Long productId = 1L;
            Long memberId = 2L;
            Mockito.when(productFavoriteRepository.findByProductProductIdAndMemberId(productId, memberId))
                    .thenReturn(Optional.empty());
            Mockito.when(productRepository.findById(productId))
                    .thenReturn(Optional.of(new Product()));
            //when
            //then
            productService.createFavorProduct(productId, memberId);
        }

        @DisplayName("좋아요 요청이 이미 있을때")
        @Test
        public void existFavor () throws Exception{
            //given
            Long productId = 1L;
            Long memberId = 2L;
            Mockito.when(productFavoriteRepository.findByProductProductIdAndMemberId(productId, memberId))
                    .thenReturn(Optional.of(new ProductFavorite()));

            //when
            //then
            assertThatThrownBy(() -> productService.createFavorProduct(productId, memberId))
                    .isInstanceOf(DuplicateException.class);

        }

        @DisplayName("제품이 존재하지 않을때")
        @Test
        public void notExistProduct() throws Exception {
            //given
            Long productId = 1L;
            Long memberId = 2L;
            Mockito.when(productFavoriteRepository.findByProductProductIdAndMemberId(productId, memberId))
                    .thenReturn(Optional.empty());
            Mockito.when(productRepository.findById(productId))
                    .thenReturn(Optional.empty());
            //when
            //then
            assertThatThrownBy(() -> productService.createFavorProduct(productId, memberId))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("제품 좋아요 취소")
    public class deleteProductFavorite {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            Long productId = 1L;
            Long memberId = 2L;
            Mockito.when(productRepository.findById(productId))
                    .thenReturn(Optional.of(new Product()));
            //when
            //then
            productService.deleteFavorProduct(productId, memberId);
        }

        @DisplayName("제품이 존재하지 않을때")
        @Test
        public void notExistProduct() throws Exception {
            //given
            Long productId = 1L;
            Long memberId = 2L;
            Mockito.when(productRepository.findById(productId))
                    .thenReturn(Optional.empty());
            //when
            //then
            assertThatThrownBy(() -> productService.deleteFavorProduct(productId, memberId))
                    .isInstanceOf(NotFoundException.class);
        }
    }



}