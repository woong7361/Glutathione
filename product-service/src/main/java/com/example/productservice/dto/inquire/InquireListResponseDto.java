package com.example.productservice.dto.inquire;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InquireListResponseDto {
    private List<InquireResponse> inquires;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class InquireResponse{
        private Long productInquireId;

        private String content;
        @Setter
        private InquireAnswerResponse answer;

        private Long memberId;
        @Setter
        private Long productId;

        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class InquireAnswerResponse {
        private Long productInquireAnswerId;

        private String content;

        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }

}
