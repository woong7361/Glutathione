package com.example.orderservice.repository;

import com.example.orderservice.entity.CouponImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponImageRepository extends JpaRepository<CouponImage, Long> {
}
