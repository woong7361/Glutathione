package com.example.productservice.controller;


import com.example.productservice.Entity.Product;
import com.example.productservice.Entity.ProductType;
import com.example.productservice.dto.common.PageRequest;
import com.example.productservice.dto.product.*;
import com.example.productservice.dto.type.ProductTypeCreateRequestDto;
import com.example.productservice.resolvehandler.AuthenticationPrincipal;
import com.example.productservice.resolvehandler.MemberPrincipal;
import com.example.productservice.resolvehandler.Principal;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 상품 관련 endpoint
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    public static final String PRODUCT_ID_RESPONSE_KEY = "productId";
    public static final String PRODUCT_TYPES_RESPONSE_KEY = "types";
    public static final String CONTENTS_RESPONSE_KEY = "contents";
    public static final String COUNT_RESPONSE_KEY = "totalCount";

    private final ProductService productService;

    /**
     * 상품 추가(생성)
     *
     * @param createRequestDto 요청 상품
     * @return 200 ok
     */
    @PostMapping("/products")
    public ResponseEntity<Map<String, Long>> createProduct(@Valid @RequestBody ProductCreateRequestDto createRequestDto) {
        Product product = productService.createProduct(createRequestDto);

        return ResponseEntity
                .ok(Map.of(PRODUCT_ID_RESPONSE_KEY, product.getProductId()));
    }

    /**
     * 상품 삭제
     *
     * @param productId 삭제할 상품 식별자
     * @return 200 ok
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.ok().build();
    }

    /**
     * 상품 수정
     *
     * @param productId 제품 식별자
     * @return 200 ok
     */
    @PutMapping("/products/{productId}")
    public ResponseEntity<Object> updateProduct(
            @Valid @RequestBody ProductUpdateRequestDto updateRequestDto,
            @PathVariable Long productId) {
        productService.updateProduct(updateRequestDto, productId);

        return ResponseEntity.ok().build();
    }

    /**
     * 제품 상세조회
     *
     * @param productId 제품 식별자
     * @return 200 ok
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDetailResponseDto> getProductDetail(@PathVariable Long productId, @MemberPrincipal Principal principal) {
        ProductDetailResponseDto responseDto = productService.getProductDetail(productId, principal.getMemberId());

        return ResponseEntity.ok(responseDto);
    }

    /**
     * 제품 타입 생성
     *
     * @param productTypeCreateRequestDto 요청 타입
     * @return 200 ok
     */
    @PostMapping("/products/types")
    public ResponseEntity<Object> createProductType(@Valid @RequestBody ProductTypeCreateRequestDto productTypeCreateRequestDto) {

        productService.createProductType(productTypeCreateRequestDto);

        return ResponseEntity.ok().build();
    }


    /**
     * 제품 타입 전체 조회
     *
     * @return 200 ok
     */
    @GetMapping("/products/types")
    public ResponseEntity<Map<String, List<ProductType>>> getProductTypes() {
        List<ProductType> productTypes = productService.getProductTypes();

        return ResponseEntity.ok(Map.of(PRODUCT_TYPES_RESPONSE_KEY, productTypes));
    }


    /**
     * 제품 검색
     *
     * @param searchRequestDto 제품 검색 요청
     * @return 200 ok & 검색 결과
     */
    @GetMapping("/products/search")
    public ResponseEntity<Map<String, List<ProductSearchResponseDto>>> productSearch(
            @ModelAttribute ProductSearchRequestDto searchRequestDto,
            @MemberPrincipal Principal principal) {
        List<ProductSearchResponseDto> result = productService.search(searchRequestDto, principal.getMemberId());

        return ResponseEntity.ok(Map.of(CONTENTS_RESPONSE_KEY, result));
    }

    /**
     * 제품 검색 개수 반환
     *
     * @param searchRequestDto 제품 검색 요청
     * @return 200 ok & 검색 결과
     */
    @GetMapping("/products/search/count")
    public ResponseEntity<Map<String, Long>> productSearchCount(
            @ModelAttribute ProductSearchRequestDto searchRequestDto) {
        Long count = productService.searchCount(searchRequestDto);

        return ResponseEntity.ok(Map.of(COUNT_RESPONSE_KEY, count));
    }

    /**
     * 제품 좋아요 추가
     *
     * @param productId 제품 식별자
     * @param principal 사용자
     * @return 200 ok
     */
    @PostMapping("/products/{productId}/favorite")
    public ResponseEntity<Object> favoriteProduct(@PathVariable Long productId, @AuthenticationPrincipal Principal principal) {
        productService.createFavorProduct(productId, principal.getMemberId());

        return ResponseEntity.ok().build();
    }

    /**
     * 제품 좋아요 삭제
     *
     * @param productId 제품 식별자
     * @param principal 사용자
     * @return 200 ok
     */
    @DeleteMapping("/products/{productId}/favorite")
    public ResponseEntity<Object> deleteFavorite(@PathVariable Long productId, @AuthenticationPrincipal Principal principal) {
        productService.deleteFavorProduct(productId, principal.getMemberId());

        return ResponseEntity.ok().build();
    }

    /**
     * 회원이 좋아요 표시한 제품 보여주기
     *
     * @param principal 인증된 회원 객체
     * @return 상품 리스트
     */
    @GetMapping("/members/products/favorites")
    public List<FavoriteProductResponseDto> getMemberFavorites(
            @AuthenticationPrincipal Principal principal, @ModelAttribute PageRequest pageRequest) {
        return productService.getProductByMemberFavorite(principal.getMemberId(), pageRequest);
    }

    @GetMapping("/products/orders/top")
    public ResponseEntity getTopOrderProducts(@MemberPrincipal Principal principal) {
        List<ProductTopResponseDto> topOrderProducts = productService.getTopOrderProducts(principal.getMemberId());

        return ResponseEntity.ok(Map.of("products", topOrderProducts));
    }

    @PostMapping("/products/order")
    public ResponseEntity<Object> reduceQuantity(@RequestBody List<ReduceQuantityRequestDto> requestDto) {
        productService.reduceQuantity(requestDto);
        return ResponseEntity.ok().build();
    }
}
