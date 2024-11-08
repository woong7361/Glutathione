package com.example.orderservice.service;

import com.example.orderservice.dto.cart.CartAddRequestDto;
import com.example.orderservice.dto.cart.CartResponseDto;
import com.example.orderservice.dto.product.ProductDetailResponseDto;
import com.example.orderservice.entity.ProductCart;
import com.example.orderservice.feign.ProductServiceClient;
import com.example.orderservice.repository.MemberCouponRepository;
import com.example.orderservice.repository.ProductCartRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 장바구니 서비스 로직
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final ProductCartRepository productCartRepository;
    private final ProductServiceClient productServiceClient;
    private final MemberCouponRepository memberCouponRepository;

    /**
     * 장바구니에 제품 추가
     *
     * @param cartAddRequestDto 추가할 제품 dto
     * @param memberId          회원 식별자
     */
    public void addCart(CartAddRequestDto cartAddRequestDto, Long memberId) {
        ProductDetailResponseDto product = productServiceClient.getProduct(cartAddRequestDto.getProductId());

        ProductCart findCart = productCartRepository.findByMemberIdAndProductId(memberId, cartAddRequestDto.getProductId())
                .orElseGet(() -> ProductCart.builder()
                        .productId(cartAddRequestDto.getProductId())
                        .memberId(memberId)
                        .quantity(0)
                        .build());

        findCart.addQuantity(cartAddRequestDto.getQuantity());

        if (verifyCartProductQuantity(findCart, product)) {
            throw new IllegalArgumentException("장바구니 수량은 제품의 재고수량을 넘거나 0이될 수 없습니다.");
        }

        productCartRepository.save(findCart);
    }



    /**
     * 장바구니 조회
     *
     * @param memberId 회원 식별자
     * @return 장바구니 리스트
     */
    public List<CartResponseDto> getCart(Long memberId) {
        List<ProductCart> carts = productCartRepository.findByMemberId(memberId);

        List<CartResponseDto> result = carts.stream()
                .map(cart -> {
                    try {
                        ProductDetailResponseDto product = productServiceClient.getProduct(cart.getProductId());
                        return new CartResponseDto(cart.getProductCartId(), cart.getQuantity(), product);
                    } catch (FeignException.BadRequest badRequest) {
                        productCartRepository.delete(cart);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        result
                .forEach(response -> response.setAvailableCoupons(
                        memberCouponRepository.findAvailableCoupons(memberId, response.getProduct().getProductId())
                ));

        return result;
    }

    /**
     * 장바구니 삭제
     * @param productCartId 장바구니 식별자
     * @param memberId 회원 식별자
     */
    public void deleteCart(Long productCartId, Long memberId) {
        productCartRepository.deleteByProductCartIdAndMemberId(productCartId, memberId);
    }


    private static boolean verifyCartProductQuantity(ProductCart findCart, ProductDetailResponseDto product) {
        return findCart.getQuantity() <= 0 || findCart.getQuantity() > product.getQuantity();
    }
}
