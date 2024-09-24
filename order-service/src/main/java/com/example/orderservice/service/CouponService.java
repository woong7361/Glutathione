package com.example.orderservice.service;

import com.example.orderservice.dto.coupon.CouponCreateRequest;
import com.example.orderservice.dto.coupon.CouponResponseDto;
import com.example.orderservice.dto.file.FileSaveResultDto;
import com.example.orderservice.entity.Coupon;
import com.example.orderservice.entity.CouponImage;
import com.example.orderservice.entity.MemberCoupon;
import com.example.orderservice.error.exception.DuplicateException;
import com.example.orderservice.error.exception.NotFoundException;
import com.example.orderservice.messageque.KafkaProducer;
import com.example.orderservice.repository.CountRepository;
import com.example.orderservice.repository.CouponRepository;
import com.example.orderservice.repository.FileStorage;
import com.example.orderservice.repository.MemberCouponRepository;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
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
    private final CountRepository countRepository;
    private final KafkaProducer kafkaProducer;


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
                .isPercent(couponCreateRequest.getIsPercent())
                .productId(couponCreateRequest.getProductId())
                .couponImage(couponImage)
                .disabled(false)
                .build();

        couponRepository.save(coupon);
    }

    /**
     * 전체 쿠폰 조회
     * @return 쿠폰 리스트
     */
    public List<Coupon> getCoupon(Boolean disabled) {
        return couponRepository.findAllWith(disabled);
    }

    /**
     * 첫 쿠폰 발급
     * @param couponId 쿠폰 아이디
     * @param memberId 회원 아이디
     */
    public void issue(Long couponId, Long memberId) {
        memberCouponRepository.findByCouponCouponIdAndMemberId(couponId, memberId)
                .ifPresent(mc -> {throw new DuplicateException("이미 발급받은 쿠폰입니다.");});

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .memberId(memberId)
                .isUsed(false)
                .build();
        memberCoupon.setCouponId(couponId);

        memberCouponRepository.save(memberCoupon);
    }

    public List<CouponResponseDto> getMemberCoupon(Long memberId) {
        return memberCouponRepository.findByMemberId(memberId)
                .stream()
                .map((mc) -> CouponResponseDto.builder()
                        .couponId(mc.getMemberCouponId())
                        .name(mc.getCoupon().getName())
                        .description(mc.getCoupon().getDescription())
                        .discount(mc.getCoupon().getDiscount())
                        .isPercent(mc.getCoupon().getIsPercent())
                        .couponImageId(mc.getCoupon().getCouponImage().getCouponImageId())
                        .productId(mc.getCoupon().getProductId())
                        .isUsed(mc.getIsUsed())
                        .build())
                .toList();
    }

    public void toggle(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException("해당하는 쿠폰이 없습니다.", couponId));

        coupon.toggle();
    }

    /**
     * 선착순 쿠폰 발급
     * @param couponId 쿠폰 식별자
     * @param memberId 회원 식별자
     */
    public void issueLimitedCoupon(Long couponId, Long memberId) {
        Long issuedCouponCount = countRepository.incrementCouponCount();
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException("coupon Not Found", couponId));

        if (coupon.getQuantity() < issuedCouponCount) {
            throw new RuntimeException("쿠폰 수량 부족");
        }

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .memberId(memberId)
                .isUsed(false)
                .build();
        memberCoupon.setCouponId(couponId);

        // kafka로 발급
        kafkaProducer.send("issue_coupon", memberCoupon);

    }

    private static byte[] getFileBytes(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
