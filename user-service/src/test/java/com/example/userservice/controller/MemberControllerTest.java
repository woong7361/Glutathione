package com.example.userservice.controller;

import com.example.userservice.entity.Member;
import com.example.userservice.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@ActiveProfiles("local")
@Import(TestSecurityConfig.class)
class MemberControllerTest {
    public static final String SIGN_UP_URI = "/members";

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    @DisplayName("회원가입 컨트롤러 테스트")
    public class CreateMemberControllerTest {
        @DisplayName("회원가입 성공")
        @Test
        public void success() throws Exception {
            //given
            Member memberRequest = Member.builder()
                    .loginId("loginId")
                    .password("password")
                    .memberName("memberName")
                    .build();

            //when
            //then
            mockMvc.perform(post(SIGN_UP_URI)
                            .content(objectMapper.writeValueAsString(memberRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }
    }
}