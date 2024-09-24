package com.example.orderservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CountRepository {
    public static final String COUNT_KEY = "count_key";
    private final RedisTemplate<String, String> redisTemplate;

    public Long incrementCouponCount() {
        return redisTemplate
                .opsForValue()
                .increment(COUNT_KEY);
    }

    public void resetCouponCount() {
        redisTemplate.delete(COUNT_KEY);
    }
}
