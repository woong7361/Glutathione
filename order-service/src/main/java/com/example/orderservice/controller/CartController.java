package com.example.orderservice.controller;

import com.example.orderservice.dto.cart.CartAddRequestDto;
import com.example.orderservice.dto.cart.CartResponseDto;
import com.example.orderservice.resolvehandler.AuthenticationPrincipal;
import com.example.orderservice.resolvehandler.Principal;
import com.example.orderservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 장바구니 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니 추가
     *
     * @return
     */
    @PostMapping("/carts")
    public ResponseEntity<Object> addCart(@RequestBody CartAddRequestDto cartAddRequestDto, @AuthenticationPrincipal Principal principal) {
        cartService.addCart(cartAddRequestDto, principal.getMemberId());

        return ResponseEntity.ok().build();
    }

    /**
     * 장바구니 조회
     *
     * @param principal 회원
     * @return 장바구니 리스트
     */
    @GetMapping("/carts")
    public ResponseEntity<Map<String, List<CartResponseDto>>> getCart(@AuthenticationPrincipal Principal principal) {
        List<CartResponseDto> carts = cartService.getCart(principal.getMemberId());

        return ResponseEntity.ok(Map.of("carts", carts));
    }

    /**
     * 장바구니 삭제
     * @param productCartId 장바구니 식별자
     * @param principal 회원
     * @return 200 ok
     */
    @DeleteMapping("/carts/{productCartId}")
    public ResponseEntity<Object> deleteCart(@PathVariable Long productCartId, @AuthenticationPrincipal Principal principal) {
        cartService.deleteCart(productCartId, principal.getMemberId());

        return ResponseEntity.ok().build();
    }
}
