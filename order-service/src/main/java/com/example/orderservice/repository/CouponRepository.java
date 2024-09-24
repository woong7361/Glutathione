package com.example.orderservice.repository;

import com.example.orderservice.entity.Coupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query("SELECT c " +
            "FROM Coupon c " +
            "WHERE c.disabled = :disabled")
    List<Coupon> findAllWith(Boolean disabled);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c WHERE c.couponId = :couponId")
    Optional<Coupon> findByCouponIdForUpdate(Long couponId);
}
