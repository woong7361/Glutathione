package com.example.orderservice.repository;

import com.example.orderservice.dto.order.TopOrderProducts;
import com.example.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 주문 repository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 회원의 모든 주문 조회
     * @param memberId 회원 식별자
     * @return 주문 리스트
     */
    List<Order> findByMemberId(Long memberId);

    /**
     * 가장 많이 주문한 제품 조회
     * @return 가장 많이 주문한 제품 리스트
     */
    @Query("SELECT op.productId as productId, count(op.productId) as count " +
            "FROM OrderProduct op " +
            "GROUP BY op.productId " +
            "order by count DESC")
    List<TopOrderProducts> getTopOrderProducts();
}
