package com.example.orderservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * redis repository
 */
@Repository
@RequiredArgsConstructor
public class CountRepository {
    public static final String COUNT_KEY = "count_key";
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * coupon 분산Lock용 연산
     * @return 현재 쿠폰 count
     */
    public Long incrementCouponCount() {
        return redisTemplate
                .opsForValue()
                .increment(COUNT_KEY);
    }

    /**
     * coupon count 초기화
     */
    public void resetCouponCount() {
        redisTemplate.delete(COUNT_KEY);
    }
}
