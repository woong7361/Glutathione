package com.example.orderservice.messageque;

import com.example.orderservice.dto.order.OrderRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * kafka producer class
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * kafka message send
     * @param topic topic
     * @param jsonString 보낼 json string
     */
    public void send(String topic, String jsonString) {
        kafkaTemplate.send(topic, jsonString);
        log.info("kafka producer send date from the order micro service: {}", jsonString);
    }


    /**
     * kafka message send
     * @param topic topic
     * @param orderRequestDto order 요청 파라미터
     * @return
     */
    public void send(String topic, OrderRequestDto orderRequestDto) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(orderRequestDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonString);
        log.info("kafka producer send date from the order micro service: {}", jsonString);
    }


    /**
     * kafka message send
     * @param topic topic
     * @param object 보낼 데이터
     */
    public void send(String topic, Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonString);
        log.info("kafka producer send date from the order micro service: {}", object);
    }

}
