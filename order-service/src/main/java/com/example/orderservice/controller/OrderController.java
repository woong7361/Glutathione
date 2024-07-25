package com.example.orderservice.controller;

import com.example.orderservice.dto.order.OrderRequestDto;
import com.example.orderservice.resolvehandler.AuthenticationPrincipal;
import com.example.orderservice.resolvehandler.Principal;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    @PostMapping("/order")
    public void order(@AuthenticationPrincipal Principal principal, @RequestBody OrderRequestDto orderRequestDto) {

        orderService.order(orderRequestDto, principal.getMemberId());

    }
}

