package com.example.orderservice.service;

import com.example.orderservice.dto.coupon.CouponResponseDto;
import com.example.orderservice.dto.order.*;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderProduct;
import com.example.orderservice.error.exception.NotFoundException;
import com.example.orderservice.feign.ProductServiceClient;
import com.example.orderservice.repository.MemberCouponRepository;
import com.example.orderservice.repository.OrderProductRepository;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final ProductServiceClient productServiceClient;
    private final MemberCouponRepository memberCouponRepository;
    private final WalletService walletService;

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public void order(OrderRequestDto orderRequestDto, Long memberId) {
        List<OrderProcessDto> process = orderRequestDto.getOrderProducts()
                .stream()
                .map(dto -> OrderProcessDto.builder()
                        .productDetail(productServiceClient.getProduct(dto.getProductId()))
                        .memberCoupon(dto.getMemberCouponId() == null ? null : memberCouponRepository.findById(dto.getMemberCouponId())
                                .orElseThrow(() -> new NotFoundException("해당하는 쿠폰이 없습니다.", dto.getMemberCouponId())))
                        .quantity(dto.getQuantity())
                        .build())
                .toList();

        Long walletAmount = walletService.getWalletAmount(memberId);

        long payment = process
                .stream().
                map(p -> {
                    if (p.getMemberCoupon() == null) {
                        return p.getQuantity() * p.getProductDetail().getUnitPrice();
                    } else {
                        return p.getQuantity() * p.getProductDetail().getUnitPrice() - p.getMemberCoupon().getCoupon().getDiscount();
                    }
                })
                .mapToInt(c -> Integer.valueOf(c.toString()))
                .sum();

        if (walletAmount < payment) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        // wallet amount -
        walletService.charge(-payment, memberId);

        // order & order product save
        Order order = Order.builder()
                .receiverName(orderRequestDto.getReceiverName())
                .receiverPhoneNumber(orderRequestDto.getReceiverPhoneNumber())
                .senderEmail(orderRequestDto.getSenderEmail())
                .senderName(orderRequestDto.getSenderName())
                .senderPhoneNumber(orderRequestDto.getSenderPhoneNumber())
                .address(orderRequestDto.getAddress())
                .addressDetail(orderRequestDto.getAddressDetail())
                .memberId(memberId)
                .build();
        orderRepository.save(order);

        process.stream()
                .map(p -> OrderProduct.builder()
                        .productId(p.getProductDetail().getProductId())
                        .memberCoupon(p.getMemberCoupon())
                        .order(order)
                        .quantity(p.getQuantity())
                        .build())
                .forEach(orderProductRepository::save);

        // coupon 사용 처리
        process.stream()
                .filter(p -> p.getMemberCoupon() != null)
                .forEach(p -> memberCouponRepository.use(p.getMemberCoupon().getMemberCouponId()));

        // product quantity -
        productServiceClient.order(orderRequestDto.getOrderProducts()
                .stream().map(dto -> ReduceQuantityRequestDto.builder()
                        .productId(dto.getProductId())
                        .quantity(dto.getQuantity())
                        .build())
                .toList());

    }

    public List<OrderResponseDto> getOrders(Long memberId) {
        List<Order> orders = orderRepository.findByMemberId(memberId);
        return orders
                .stream().map(order -> OrderResponseDto.builder()
                        .senderName(order.getSenderName())
                        .senderEmail(order.getSenderEmail())
                        .senderPhoneNumber(order.getSenderPhoneNumber())
                        .receiverName(order.getReceiverName())
                        .receiverPhoneNumber(order.getReceiverPhoneNumber())
                        .address(order.getAddress())
                        .addressDetail(order.getAddressDetail())
                        .orderProducts(order.getOrderProduct().stream().map(
                                        p -> OrderResponseDto.OrderProductsDto.builder()
                                                .quantity(p.getQuantity())
                                                .productDetailResponseDto(productServiceClient.getProduct(p.getProductId()))
                                                .couponResponseDto(p.getMemberCoupon() == null ? null : CouponResponseDto.builder()
                                                        .name(p.getMemberCoupon().getCoupon().getName())
                                                        .description(p.getMemberCoupon().getCoupon().getDescription())
                                                        .couponId(p.getMemberCoupon().getMemberCouponId())
                                                        .couponImageId(p.getMemberCoupon().getCoupon().getCouponImage().getCouponImageId())
                                                        .isUsed(p.getMemberCoupon().getIsUsed())
                                                        .discount(p.getMemberCoupon().getCoupon().getDiscount())
                                                        .productId(p.getProductId())
                                                        .isPercent(p.getMemberCoupon().getCoupon().getIsPercent())
                                                        .build())
                                                .build())
                                .toList())
                        .build())
                .toList();

    }

    public TopOrderProducts getTopOrderProducts() {
        return orderRepository.getTopOrderProducts();

    }
}





