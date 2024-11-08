package com.example.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 주문
 */
@Entity(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long memberId;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProduct;

    private String senderName;
    private String senderPhoneNumber;
    private String senderEmail;

    private String receiverName;
    private String receiverPhoneNumber;

    private String address;
    private String addressDetail;
}
