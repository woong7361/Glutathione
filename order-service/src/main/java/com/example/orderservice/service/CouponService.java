package com.example.orderservice.service;

import com.example.orderservice.dto.coupon.CouponCreateRequest;
import com.example.orderservice.dto.file.FileSaveResultDto;
import com.example.orderservice.entity.Coupon;
import com.example.orderservice.entity.CouponImage;
import com.example.orderservice.entity.MemberCoupon;
import com.example.orderservice.error.exception.DuplicateException;
import com.example.orderservice.repository.CouponRepository;
import com.example.orderservice.repository.FileStorage;
import com.example.orderservice.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 쿠폰 서비스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final FileStorage fileStorage;


    /**
     * 관리자의 쿠폰 등록
     *
     * @param couponCreateRequest 쿠폰 생성 요청 정보
     * @param multipartFile 쿠폰 이미지
     */
    public void createCoupon(CouponCreateRequest couponCreateRequest, MultipartFile multipartFile) {
        FileSaveResultDto saveResult = fileStorage.save(getFileBytes(multipartFile), multipartFile.getOriginalFilename());

        CouponImage couponImage = CouponImage.builder()
                .originalName(multipartFile.getOriginalFilename())
                .path(saveResult.getPath())
                .physicalName(saveResult.getPhysicalName())
                .build();

        Coupon coupon = Coupon.builder()
                .name(couponCreateRequest.getName())
                .description(couponCreateRequest.getDescription())
                .discount(couponCreateRequest.getDiscount())
                .productId(couponCreateRequest.getProductId())
                .couponImage(couponImage)
                .build();

        couponRepository.save(coupon);
    }

    /**
     * 전체 쿠폰 조회
     * @return 쿠폰 리스트
     */
    public List<Coupon> getCoupon() {
        return couponRepository.findAll();
    }

    /**
     * 첫 쿠폰 발급
     * @param couponId 쿠폰 아이디
     * @param memberId 회원 아이디
     */
    public void issue(Long couponId, Long memberId) {
        memberCouponRepository.findByCouponIdAndMemberId(couponId, memberId)
                .ifPresent(mc -> {throw new DuplicateException("이미 발급받은 쿠폰입니다.");});

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .memberId(memberId)
                .couponId(couponId)
                .build();

        memberCouponRepository.save(memberCoupon);
    }

    private static byte[] getFileBytes(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
