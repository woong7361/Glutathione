package com.example.orderservice.controller;

import com.example.orderservice.dto.order.OrderRequestDto;
import com.example.orderservice.dto.order.OrderResponseDto;
import com.example.orderservice.dto.order.PaymentConfirmRequest;
import com.example.orderservice.dto.order.TopOrderProducts;
import com.example.orderservice.resolvehandler.AuthenticationPrincipal;
import com.example.orderservice.resolvehandler.Principal;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    public static final String ORDERS_RESPONSE_KEY = "orders";
    private final OrderService orderService;

    /**
     * 주문
     *
     * @param principal       주문하는 사용자
     * @param orderRequestDto 주문 요청 정보
     * @return
     */
    @PostMapping("/orders")
    public ResponseEntity<Object> order(@AuthenticationPrincipal Principal principal, @RequestBody OrderRequestDto orderRequestDto) {

        orderService.order(orderRequestDto, principal.getMemberId());

        return ResponseEntity.ok().build();
    }

    /**
     * 자신이 한 주문들 조회
     * @param principal 인증된 회원
     * @return 주문한 영수증들
     */
    @GetMapping("/members/orders")
    public ResponseEntity<Map<String, List<OrderResponseDto>>> getOrders(@AuthenticationPrincipal Principal principal) {
        List<OrderResponseDto> orders = orderService.getOrders(principal.getMemberId());

        return ResponseEntity.ok(Map.of(ORDERS_RESPONSE_KEY, orders));
    }

    /**
     * 가장 주문을 많이 한사람을 반환
     */
    @GetMapping("/orders/top")
    public ResponseEntity<List<TopOrderProducts>> getTopOrderProducts() {
        List<TopOrderProducts> topOrderProducts = orderService.getTopOrderProducts();

        return ResponseEntity.ok(topOrderProducts);
    }
}

