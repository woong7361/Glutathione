package com.example.orderservice.controller;

import com.example.orderservice.dto.order.OrderRequestDto;
import com.example.orderservice.dto.order.OrderResponseDto;
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

    @GetMapping("/members/orders")
    public ResponseEntity<Map<String, List<OrderResponseDto>>> getOrders(@AuthenticationPrincipal Principal principal) {
        List<OrderResponseDto> orders = orderService.getOrders(principal.getMemberId());

        return ResponseEntity.ok(Map.of(ORDERS_RESPONSE_KEY, orders));
    }

    @GetMapping("/orders/top")
    public ResponseEntity<List<TopOrderProducts>> getTopOrderProducts() {
        List<TopOrderProducts> topOrderProducts = orderService.getTopOrderProducts();

        return ResponseEntity.ok(topOrderProducts);
    }
}

