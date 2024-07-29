package com.example.orderservice.repository;

import com.example.orderservice.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query("SELECT c " +
            "FROM Coupon c " +
            "WHERE c.disabled = :disabled")
    List<Coupon> findAllWith(Boolean disabled);
}
