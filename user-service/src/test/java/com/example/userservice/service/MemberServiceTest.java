package com.example.userservice.service;

import com.example.userservice.entity.Member;
import com.example.userservice.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class MemberServiceTest {
    private MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
    private MemberService memberService = new MemberService(memberRepository, bCryptPasswordEncoder);

    @Nested
    @DisplayName("회원 생성 테스트")
    public class CreateMemberTest {
        @DisplayName("회원 생성")
        @Test
        public void success() throws Exception {
            //given
            Member member = Member.builder()
                    .loginId("loginId")
                    .password("password")
                    .memberName("memberName")
                    .build();
            String encodedPassword = "secretPassword";

            Mockito.when(bCryptPasswordEncoder.encode(member.getPassword()))
                    .thenReturn(encodedPassword);

            //when
            memberService.createMember(member);

            //then
            assertThat(member.getPassword()).isEqualTo(encodedPassword);
        }
    }

    @Nested
    @DisplayName("로그인 아이디 중복 확인 테스트")
    public class CheckDuplicateLoginId {
        @DisplayName("중복이 아닐때")
        @Test
        public void notDuplicate() throws Exception {
            //given
            String loginId = "loginId";
            Mockito.when(memberRepository.findByLoginId(loginId))
                    .thenReturn(Optional.empty());

            //when
            Boolean result = memberService.isDuplicateLoginId(loginId);

            //then
            assertThat(result).isFalse();
        }

        @DisplayName("중복일때")
        @Test
        public void duplicate() throws Exception{
            //given
            String loginId = "loginId";
            Mockito.when(memberRepository.findByLoginId(loginId))
                    .thenReturn(Optional.of(Member.builder().build()));

            //when
            Boolean result = memberService.isDuplicateLoginId(loginId);

            //then
            assertThat(result).isTrue();
        }
    }

}