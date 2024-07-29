package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private Long memberId;
    private String loginId;
    private String memberName;

    private String phoneNumber;
    private String email;
    private String postNumber;
    private String address;
    private String addressDetail;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
