package com.example.productservice.feign;

import com.example.productservice.dto.member.MemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface MemberServiceClient {

    @GetMapping("/members/{memberId}")
    MemberDto getMember(@PathVariable Long memberId);
}
