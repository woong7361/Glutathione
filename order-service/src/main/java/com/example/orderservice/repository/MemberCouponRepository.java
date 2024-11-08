package com.example.orderservice.repository;

import com.example.orderservice.dto.coupon.CouponResponseDto;
import com.example.orderservice.entity.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 회원 쿠폰 repository
 */
@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    /**
     * couponId와 memberId로 memberCoupon 조회
     * @param couponId coupon 식별자
     * @param memberId member 식별자
     * @return 회원 쿠폰
     */
    Optional<MemberCoupon> findByCouponCouponIdAndMemberId(Long couponId, Long memberId);

    /**
     * memberId로 memberCoupon 조회
     * @param memberId member 식별자
     * @return 회원 쿠폰
     */
    List<MemberCoupon> findByMemberId(Long memberId);

    /**
     * 사용 가능한 쿠폰 조회
     * @param memberId 요청 회원 식별자
     * @param productId 해당 상품 식별자
     * @return 회원 쿠폰
     */
    @Query("SELECT mc " +
            "FROM MemberCoupon mc " +
            "JOIN FETCH Coupon c on mc.coupon.couponId = c.couponId " +
            "WHERE mc.memberId = :memberId " +
            "   AND (c.productId = :productId OR c.productId IS NULL) " +
            "   AND mc.isUsed = false")
    List<MemberCoupon> findAvailableCoupons(Long memberId, Long productId);

    /**
     * 쿠폰 사용처리
     * @param memberCouponId 사용할 쿠폰 식별자
     */
    @Modifying
    @Query("UPDATE MemberCoupon mc " +
            "SET mc.isUsed = true " +
            "WHERE mc.memberCouponId = :memberCouponId")
    void use(Long memberCouponId);

    /**
     * 발급된 쿠폰 수 조회
     * @param couponId 쿠폰 식별자
     * @return 발급된 쿠폰 수
     */
    @Query("SELECT COUNT(*) " +
            "FROM MemberCoupon mc " +
            "WHERE mc.coupon.couponId = :couponId")
    Long countByCouponId(Long couponId);
}
