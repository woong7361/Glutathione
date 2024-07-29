package com.example.orderservice.repository;

import com.example.orderservice.dto.order.TopOrderProducts;
import com.example.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMemberId(Long memberId);

    @Query("SELECT op.productId as productId, count(op.productId) as count " +
            "FROM OrderProduct op " +
            "GROUP BY op.productId " +
            "order by count DESC")
    List<TopOrderProducts> getTopOrderProducts();
}
