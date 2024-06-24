package com.example.userservice.controller;

import com.example.userservice.entity.Member;
import com.example.userservice.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@ActiveProfiles("local")
@Import(TestSecurityConfig.class)
class MemberControllerTest {
    public static final String SIGN_UP_URI = "/members";
    public static final String CHECK_DUPLICATE_LOGIN_ID_URI = "/members/loginId";

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

    @Nested
    @DisplayName("로그인 아이디 중복 확인 컨트롤러 테스트")
    public class CheckDuplicateLoginId {
        @DisplayName("중복일때")
        @Test
        public void duplicate() throws Exception {
            //given
            String duplicateLoginId = "duplicate";

            Mockito.when(memberService.isDuplicateLoginId(duplicateLoginId))
                    .thenReturn(true);

            //when
            ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get(CHECK_DUPLICATE_LOGIN_ID_URI)
                    .param("loginId", duplicateLoginId));

            //then
            action
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.duplicate").value(true));
        }

        @DisplayName("중복이 아닐때")
        @Test
        public void notDuplicate() throws Exception{
            //given
            String notDuplicateLoginId = "duplicate";

            Mockito.when(memberService.isDuplicateLoginId(notDuplicateLoginId))
                    .thenReturn(false);

            //when
            ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get(CHECK_DUPLICATE_LOGIN_ID_URI)
                    .param("loginId", notDuplicateLoginId));

            //then
            action
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.duplicate").value(false));
        }
    }
}