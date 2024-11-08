package com.example.productservice.messageque;

import com.example.productservice.dto.order.OrderRequestDto;
import com.example.productservice.repository.ProductRepository;
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
    private final ProductRepository productRepository;

    /**
     * rollback 요청으로 인한 제품 수량 업데이트
     * @param kafkaMessage 카프카 메시지
     */
    @KafkaListener(topics = "order-rollback", groupId = "consumerGroupId")
    public void updateQty(String kafkaMessage) {
        log.info("order rollback-event receive: {}", kafkaMessage);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderRequestDto orderRequestDto = objectMapper.readValue(kafkaMessage, OrderRequestDto.class);
            orderRequestDto.getOrderProducts().stream()
                    .forEach(p -> {
                        productRepository.findById(p.getProductId())
                                .ifPresent(product -> product.addQuantity(p.getQuantity()));
                    });

        } catch (Exception e) {
            log.error("order rollback 실패 관리자 문의 필요: message: {}", kafkaMessage);
        }


    }
}
