package com.example.userservice.service;

import com.example.userservice.dto.MemberResponseDto;
import com.example.userservice.entity.Member;
import com.example.userservice.error.exception.NotFoundException;
import com.example.userservice.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;


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


    @Nested
    @DisplayName("회원 수정 테스트")
    public class updateMember {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            Member targetMember = Member.builder()
                    .memberId(1L)
                    .password("targetPassword")
                    .memberName("targetName")
                    .build();

            Mockito.when(memberRepository.findById(targetMember.getMemberId()))
                    .thenReturn(Optional.of(targetMember));

            Member updateMember = Member.builder()
                    .memberId(1L)
                    .password("updatePassword")
                    .memberName("updateName")
                    .build();

            //when
            memberService.updateMember(1L, updateMember);

            //then
            Assertions.assertThat(targetMember).usingRecursiveComparison().isEqualTo(updateMember);
        }

        @DisplayName("식별자에 해당하는 회원이 없을때")
        @Test
        public void notExistMember() throws Exception{
            //given
            Mockito.when(memberRepository.findById(any()))
                    .thenReturn(Optional.empty());

            //when
            //then
            Assertions.assertThatThrownBy(() -> memberService.updateMember(1L, Member.builder().build()))
                    .isInstanceOf(NotFoundException.class);
        }
    }


    @Nested
    @DisplayName("회원 삭제 테스트")
    public class deleteMember {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            Member targetMember = Member.builder()
                    .memberId(1L)
                    .loginId("targetId")
                    .password("targetPassword")
                    .memberName("targetName")
                    .build();

            Mockito.when(memberRepository.findById(targetMember.getMemberId()))
                    .thenReturn(Optional.of(targetMember));
            //when
            //then
            memberService.deleteMember(1L);
        }

        @DisplayName("식별자에 해당하는 회원이 없을때")
        @Test
        public void notExistMember() throws Exception{
            //given
            Mockito.when(memberRepository.findById(any()))
                    .thenReturn(Optional.empty());

            //when
            //then
            Assertions.assertThatThrownBy(() -> memberService.deleteMember(1L))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("회원 정보 조회 테스트")
    public class getMemberTest {
        @DisplayName("정상 처리")
        @Test
        public void success() throws Exception {
            //given
            Member targetMember = Member.builder()
                    .memberId(1L)
                    .loginId("targetId")
                    .password("targetPassword")
                    .memberName("targetName")
                    .build();

            Mockito.when(memberRepository.findById(targetMember.getMemberId()))
                    .thenReturn(Optional.of(targetMember));

            //when
            MemberResponseDto responseDto = memberService.getMember(targetMember.getMemberId());

            //then
            assertThat(responseDto.getMemberId()).isEqualTo(targetMember.getMemberId());
            assertThat(responseDto.getLoginId()).isEqualTo(targetMember.getLoginId());
            assertThat(responseDto.getMemberName()).isEqualTo(targetMember.getMemberName());
        }

        @DisplayName("해당하는 회원이 없을때")
        @Test
        public void notFound() throws Exception{
            //given
            Mockito.when(memberRepository.findById(any()))
                    .thenReturn(Optional.empty());

            //when
            //then
            Assertions.assertThatThrownBy(() -> memberService.getMember(1L))
                    .isInstanceOf(NotFoundException.class);
        }
    }

}