package com.example.productservice.service;

import com.example.productservice.Entity.Product;
import com.example.productservice.Entity.ProductFavorite;
import com.example.productservice.Entity.ProductType;
import com.example.productservice.converter.ProductConverter;
import com.example.productservice.dto.product.ProductCreateRequestDto;
import com.example.productservice.dto.product.ProductDetailResponseDto;
import com.example.productservice.dto.product.ProductSearchRequestDto;
import com.example.productservice.dto.type.ProductTypeCreateRequestDto;
import com.example.productservice.error.exception.DuplicateException;
import com.example.productservice.error.exception.NotFoundException;
import com.example.productservice.repository.ProductFavoriteRepository;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductFavoriteRepository productFavoriteRepository;

    /**
     * 제품 생성
     * @param createRequestDto 제품 생성 요청
     * @return 저장된 제품
     */
    public Product createProduct(ProductCreateRequestDto createRequestDto) {
        Product product = ProductConverter.fromCreateRequestDto(createRequestDto);

        productRepository.save(product);
        log.info("{} product save successful", product.getName());

        return product;
    }

    /**
     * 제품 타입 생성
     * @param createRequestDto 요청 타입
     */
    public void createProductType(ProductTypeCreateRequestDto createRequestDto) {
        productTypeRepository.save(ProductType.builder()
                .type(createRequestDto.getProductType())
                .build());
    }

    /**
     * 제품 타입 전체 조회
     *
     * @return all product type
     */
    public List<ProductType> getProductTypes() {
        return productTypeRepository.findAll();
    }

    /**
     * 제품 상세 조회
     *
     * @param productId 제품 식별자
     * @return response
     */
    public ProductDetailResponseDto getProductDetail(Long productId) {
        return productRepository.findById(productId)
                .map(product -> ProductConverter.toProductDetailResponseDto(product))
                .orElseThrow(() -> new NotFoundException("product not exist", productId));
    }

    /**
     * 제품 검색
     *
     * @param searchRequestDto 제품 검색 요청 인자
     */
    public List<ProductDetailResponseDto> search(ProductSearchRequestDto searchRequestDto) {
        List<Product> search = productRepository.search(searchRequestDto);
        return search.stream().map(pr -> ProductConverter.toProductDetailResponseDto(pr))
                .collect(Collectors.toList());
    }

    /**
     * 제품 좋아요 추가
     * @param productId 제품 식별자
     * @param memberId 사용자 식별자
     */
    public void createFavorProduct(Long productId, Long memberId) {
        productFavoriteRepository.findByProductProductIdAndMemberId(productId, memberId)
                .ifPresent( x -> {throw new DuplicateException("중복된 좋아요 요청입니다.");});

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("product not exist", productId));

        ProductFavorite productFavorite = ProductFavorite.builder()
                .product(product)
                .memberId(memberId)
                .build();
        productFavoriteRepository.save(productFavorite);
    }

    /**
     * 제품 좋아요 취소
     * @param productId 제품 식별자
     * @param memberId 회원 식별자
     */
    public void deleteFavorProduct(Long productId, Long memberId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("product not exist", productId));

        productFavoriteRepository.deleteByProductProductIdAndMemberId(productId, memberId);
    }
}
