package com.example.orderservice.service;

import com.example.orderservice.entity.Coupon;
import com.example.orderservice.repository.CountRepository;
import com.example.orderservice.repository.CouponRepository;
import com.example.orderservice.repository.MemberCouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value = "local")
class CouponServiceTest {

    @Autowired
    CouponService couponService;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    MemberCouponRepository memberCouponRepository;
    @Autowired
    CountRepository countRepository;

    @DisplayName("선착순 쿠폰 테스트")
    @Test
    public void limitedCouponTest() throws Exception{
        //given
        Coupon coupon = Coupon.builder()
                .description("desc")
                .discount(100)
                .disabled(false)
                .isPercent(false)
                .name("test coupon")
                .quantity(2000L)
                .build();
        couponRepository.save(coupon);

        countRepository.resetCouponCount();

        // 100개의 스레드풀
        ExecutorService executorService = Executors.newFixedThreadPool(300);
        // 1000번의 작업 진행
        int threadCount = 2000;
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (long count = 0; count < threadCount; count++) {
            Long memberId = count;

            executorService.submit(() -> {
                try {
                    couponService.issueLimitedCoupon(coupon.getCouponId(), memberId);
                } catch (Exception e) {
                    // continue
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        //then
        Long couponCount = memberCouponRepository.countByCouponId(coupon.getCouponId());
        System.out.println("couponCount = " + couponCount);

//        Thread.sleep(2000);
//        Assertions.assertThat(couponCount).isLessThanOrEqualTo(100);
    }

}