package com.example.userservice.controller;

import com.example.userservice.entity.Member;
import com.example.userservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 관련 엔드포인트
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("health-check")
    public String healthCheck() {
        log.info("health-check");

        return "good";
    }

    /**
     * 회원 가입
     * @param member 회원가입 요청 파라미터
     * @return 201 created
     */
    @PostMapping("/members")
    public ResponseEntity<Object> createMember(@RequestBody Member member) {
        memberService.createMember(member);

        return ResponseEntity.status(201)
                .build();
    }
}
