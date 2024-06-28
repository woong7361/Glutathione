package com.example.productservice.service;

import com.example.productservice.Entity.Product;
import com.example.productservice.Entity.ProductType;
import com.example.productservice.converter.ProductConverter;
import com.example.productservice.dto.Product.ProductCreateRequestDto;
import com.example.productservice.dto.Product.ProductDetailResponseDto;
import com.example.productservice.dto.type.ProductTypeCreateRequestDto;
import com.example.productservice.error.exception.NotFoundException;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;

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
}
