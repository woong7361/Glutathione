package com.example.productservice.dto.member;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberDto {
    private Long memberId;
    private String loginId;
    private String memberName;
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
}
