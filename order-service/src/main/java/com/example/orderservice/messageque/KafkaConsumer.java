package com.example.orderservice.messageque;

import com.example.orderservice.dto.order.OrderRequestDto;
import com.example.orderservice.entity.MemberCoupon;
import com.example.orderservice.repository.MemberCouponRepository;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * kafka consumer class
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final OrderService orderService;
    private final MemberCouponRepository memberCouponRepository;
    private final KafkaProducer kafkaProducer;

    /**
     * order rollback request event
     * @param kafkaMessage rollback 해야할 order (String)
     */
    @KafkaListener(topics = "order-request", groupId = "consumerGroupId")
    public void updateQty(String kafkaMessage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderRequestDto orderRequestDto = objectMapper.readValue(kafkaMessage, OrderRequestDto.class);
            orderService.order(orderRequestDto, orderRequestDto.getMemberId());
        } catch (Exception e) {
            log.error("order error -> rollback required: {}", e.getMessage());
            kafkaProducer.send("order-rollback", kafkaMessage);
        }
    }

    /**
     * 쿠폰 발급 event
     * @param kafkaMessage 발급할 쿠폰 (String)
     */
    @KafkaListener(topics = "issue_coupon", groupId = "consumerGroupId")
    public void issue_coupon(String kafkaMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MemberCoupon memberCoupon = objectMapper.readValue(kafkaMessage, MemberCoupon.class);
            memberCouponRepository.save(memberCoupon);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
