package com.example.productservice.service;

import com.example.productservice.Entity.ProductInquire;
import com.example.productservice.Entity.ProductInquireAnswer;
import com.example.productservice.converter.InquireConverter;
import com.example.productservice.dto.inquire.InquireListResponseDto;
import com.example.productservice.dto.member.MemberDto;
import com.example.productservice.error.exception.NotFoundException;
import com.example.productservice.feign.MemberServiceClient;
import com.example.productservice.repository.ProductInquireAnswerRepository;
import com.example.productservice.repository.ProductInquireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InquireService {
    private final ProductInquireRepository productInquireRepository;
    private final ProductInquireAnswerRepository productInquireAnswerRepository;
    private final MemberServiceClient memberServiceClient;

    /**
     * 제품 문의 작성
     * @param productId 제품 식별자
     * @param memberId 작성 요청한 회원 식별자
     * @param content 문의 내용
     */
    public void createProductInquire(Long productId, Long memberId, String content) {
        ProductInquire inquire = ProductInquire.builder()
                .content(content)
                .memberId(memberId)
                .build();
        inquire.setProductId(productId);

        productInquireRepository.save(inquire);
    }

    /**
     * 제품 문의 삭제
     * @param inquireId 문의 식별자
     * @param memberId 요청한 회원 식별자
     */
    public void deleteProductInquire(Long inquireId, Long memberId) {
        productInquireRepository.findByProductInquireIdAndMemberId(inquireId, memberId)
                .orElseThrow(() -> new NotFoundException("해당하는 문의글이 없거나, 작성자가 다릅니다.", inquireId));
        productInquireRepository.deleteById(inquireId);
    }

    /**
     * 문의 답변 작성
     * @param inquireId 문의 식별자
     * @param content 문의 답변 내용
     */
    public void createProductInquireAnswer(Long inquireId, String content) {
        ProductInquire productInquire = productInquireRepository.findById(inquireId)
                .orElseThrow(() -> new NotFoundException("해당하는 문의를 찾을 수 없습니다.", inquireId));

        ProductInquireAnswer inquireAnswer = ProductInquireAnswer.builder()
                .content(content)
                .productInquire(productInquire)
                .productId(productInquire.getProduct().getProductId())
                .build();

        productInquireAnswerRepository.save(inquireAnswer);
    }

    /**
     * 문의 답변 삭제
     * @param answerId 삭제할 문의 답변 식별자
     */
    public void deleteProductInquireAnswer(Long answerId) {
        productInquireAnswerRepository.deleteById(answerId);
    }

    /**
     * 제품 문의 조회
     * @param productId 제품 식별자
     * @return 제품 문의들
     */
    public InquireListResponseDto getInquires(Long productId) {
        List<InquireListResponseDto.InquireResponse> responseList = productInquireRepository.findByProductProductId(productId)
                .stream().map((inquire) -> {
                    InquireListResponseDto.InquireResponse inquireResponse =
                            InquireConverter.toInquireResponse(inquire);
                    inquireResponse.setAnswer(InquireConverter.toInquireAnswerResponse(inquire.getProductInquireAnswer()));
                    inquireResponse.setProductId(inquire.getProduct().getProductId());

                    return inquireResponse;
                }).toList();

        responseList.forEach(inq -> {
            //TODO 쿼리가 많이 나간다.
            MemberDto memberDto = memberServiceClient.getMember(inq.getMemberId());
            inq.setMemberName(memberDto.getMemberName());
        });

        return InquireListResponseDto.builder()
                .inquires(responseList)
                .build();
    }
}
