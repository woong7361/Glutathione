package com.example.productservice.feign;

import com.example.productservice.dto.member.MemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 사용자 서비스와의 통신 인터페이스
 */
@FeignClient(name = "user-service")
public interface MemberServiceClient {

    /**
     * 회원 조회
     * @param memberId 회원 식별자
     * @return 회원 DTO
     */
    @GetMapping("/members/{memberId}")
    MemberDto getMember(@PathVariable Long memberId);
}
