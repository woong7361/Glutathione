package com.example.orderservice.repository;

import com.example.orderservice.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 주문한 물품 repository
 */
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
