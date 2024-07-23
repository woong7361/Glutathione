package com.example.orderservice.repository;

import com.example.orderservice.entity.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    Optional<MemberCoupon> findByCouponIdAndMemberId(Long couponId, Long memberId);
}
