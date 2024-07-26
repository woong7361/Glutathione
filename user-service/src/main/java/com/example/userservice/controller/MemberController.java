package com.example.userservice.controller;

import com.example.userservice.dto.MemberResponseDto;
import com.example.userservice.entity.Member;
import com.example.userservice.security.MemberPrincipal;
import com.example.userservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.userservice.constant.ResponseConstant.DUPLICATE_LOGIN_ID_RESPONSE_KEY;

/**
 * 회원 관련 엔드포인트
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원 가입
     *
     * @param member 회원가입 요청 파라미터
     * @return 201 created
     */
    @PostMapping("/members")
    public ResponseEntity<Object> createMember(@RequestBody Member member) {
        memberService.createMember(member);

        return ResponseEntity.ok()
                .build();
    }

    /**
     * 회원 조회
     *
     * @param memberId 조회할 회원 식별자
     * @return 200 OK, 회원 정보
     */
    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long memberId, @AuthenticationPrincipal MemberPrincipal memberPrincipal) {

        MemberResponseDto responseDto = memberService.getMember(memberId);

        return ResponseEntity.ok(responseDto);
    }

    /**
     * 회원 정보 업데이트
     * @param memberId 회원 식별자
     * @param member 회원 정보 수정 요청 파라미터
     * @return 200 ok
     */
    @PutMapping("/members/{memberId}")
    public ResponseEntity<Object> updateMember(
            @PathVariable Long memberId,
            @RequestBody Member member) {
        memberService.updateMember(memberId, member);

        return ResponseEntity.ok()
                .build();
    }

    /**
     * 회원 탈퇴
     * @param memberId 회원 식별자
     * @return 200 OK
     */
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Object> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);

        return ResponseEntity.ok()
                .build();
    }

    /**
     * 로그인 아이디 중복 확인
     * @param loginId target login Id
     * @return 중복 결과 json
     */
    @GetMapping("/members/loginId")
    public ResponseEntity<Map<String, Boolean>> checkDuplicateLoginId(@RequestParam String loginId) {
        Boolean result = memberService.isDuplicateLoginId(loginId);

        return ResponseEntity
                .ok()
                .body(Map.of(DUPLICATE_LOGIN_ID_RESPONSE_KEY, result));
    }
}
