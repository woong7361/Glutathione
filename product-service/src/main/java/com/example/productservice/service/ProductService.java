package com.example.productservice.service;

import com.example.productservice.Entity.Product;
import com.example.productservice.Entity.ProductFavorite;
import com.example.productservice.Entity.ProductType;
import com.example.productservice.converter.ProductConverter;
import com.example.productservice.dto.common.PageRequest;
import com.example.productservice.dto.order.OrderRequestDto;
import com.example.productservice.dto.product.*;
import com.example.productservice.dto.type.ProductTypeCreateRequestDto;
import com.example.productservice.error.exception.DuplicateException;
import com.example.productservice.error.exception.NotFoundException;
import com.example.productservice.feign.OrderServiceClient;
import com.example.productservice.messageque.KafkaProducer;
import com.example.productservice.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {
    public static final String THUMBNAIL_ = "thumbnail_";
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductFavoriteRepository productFavoriteRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductStyleRepository productStyleRepository;
    private final ProductInquireRepository productInquireRepository;
    private final OrderServiceClient orderServiceClient;
    private final KafkaProducer kafkaProducer;

    /**
     * 제품 생성
     * @param createRequestDto 제품 생성 요청
     * @return 저장된 제품
     */
    public Product createProduct(ProductCreateRequestDto createRequestDto) {
        Product product = ProductConverter.fromCreateRequestDto(createRequestDto);
        productRepository.save(product);

        productImageRepository.findAllById(createRequestDto.getContentImageIds())
                .forEach(img -> img.setProductId(product.getProductId()));

        productImageRepository.findById(createRequestDto.getThumbnailImageId())
                .ifPresent(img -> img.setProductId(product.getProductId()));

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
     * @param memberId 회원 식별자
     * @return response
     */
    public ProductDetailResponseDto getProductDetail(Long productId, Long memberId) {

        ProductDetailResponseDto result = productRepository.findByIdWithThumbnail(productId, THUMBNAIL_)
                .map(product -> ProductConverter.toProductDetailResponseDto(product))
                .orElseThrow(() -> new NotFoundException("product not exist", productId));

        ProductFavoriteDto favor = productRepository.findFavoriteCountById(productId, memberId);
        result.setFavorCount(favor.getFavoriteCount());
        result.setIsFavor(favor.getIsFavor());
        return result;
    }

    /**
     * 제품 검색
     *
     * @param searchRequestDto 제품 검색 요청 인자
     * @param memberId         검색한 회원 식별자
     */
    public List<ProductSearchResponseDto> search(ProductSearchRequestDto searchRequestDto, Long memberId) {
        return productRepository.search(searchRequestDto, memberId);
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

    /**
     * 제품 삭제
     * @param productId 삭제할 제품 식별자
     */
    public void deleteProduct(Long productId) {
        productImageRepository.deleteByproductId(productId);
        productStyleRepository.deleteByProductProductId(productId);
        productFavoriteRepository.deleteByProductProductId(productId);
        productInquireRepository.deleteByProductProductId(productId);

        productRepository.deleteById(productId);

    }

    /**
     * 제품 업데이트
     * @param updateRequestDto 업데이트 요청 파라미터
     * @param productId 제품 식별자
     */
    public void updateProduct(ProductUpdateRequestDto updateRequestDto, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("product not exist", productId));

        product.update(updateRequestDto);
        productRepository.save(product);

        productImageRepository.findAllById(updateRequestDto.getContentImageIds())
                .forEach(img -> {
                    img.setProductId(product.getProductId());
                    productImageRepository.save(img);
                });
        productImageRepository.findById(updateRequestDto.getThumbnailImageId())
                .filter(img -> img.getProductId() == null)
                .ifPresent(img -> {
                    img.setProductId(product.getProductId());
                    productImageRepository.save(img);
                });

        productImageRepository.findAllById(updateRequestDto.getDeleteImageIds())
                .forEach(img -> {
                    img.setProduct(null);
                    productImageRepository.save(img);
                });
    }

    /**
     * 회원이 좋아하는 제품 조회
     *
     * @param memberId    회원 식별자
     * @param pageRequest 페이징 요청 인자
     * @return 제품 리스트
     */
    public List<FavoriteProductResponseDto> getProductByMemberFavorite(Long memberId, PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize());
        List<FavoriteProductResponseDto> productByMemberFavorites
                = productRepository.findProductByMemberFavorite(memberId, pageable);
        return productByMemberFavorites;
    }

    public void reduceQuantity(List<ReduceQuantityRequestDto> requestDto) {
        // TODO Quantity > 0 엔티티에 설정해야한다.
        requestDto
                .forEach(r -> productRepository.reduceQuantity(r.getProductId(), r.getQuantity()));

    }

    public List<ProductTopResponseDto> getTopOrderProducts(Long memberId) {
        return orderServiceClient.getTopOrderProducts()
                .stream().map(t -> {
                    ProductDetailResponseDto result = productRepository.findByIdWithThumbnail(t.getProductId(), THUMBNAIL_)
                            .map(product -> ProductConverter.toProductDetailResponseDto(product))
                            .orElseThrow(() -> new NotFoundException("product not exist", t.getProductId()));

                    ProductFavoriteDto favor = productRepository.findFavoriteCountById(t.getProductId(), memberId);
                    result.setFavorCount(favor.getFavoriteCount());
                    result.setIsFavor(favor.getIsFavor());

                    return ProductTopResponseDto.builder()
                            .product(result)
                            .count(t.getCount())
                            .build();

                })
                .toList();
    }

    public Long searchCount(ProductSearchRequestDto searchRequestDto) {
        return productRepository.searchCount(searchRequestDto);
    }

    public void order(OrderRequestDto orderRequestDto) {
        reduceQuantity(orderRequestDto.getOrderProducts().stream()
                .map((product) -> new ReduceQuantityRequestDto(product.getProductId(), product.getQuantity()))
                .collect(Collectors.toList()));

        kafkaProducer.send("order-request", orderRequestDto);
    }
}
