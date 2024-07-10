package com.example.productservice.converter;

import com.example.productservice.Entity.Product;
import com.example.productservice.Entity.ProductInquire;
import com.example.productservice.Entity.ProductInquireAnswer;
import com.example.productservice.dto.inquire.InquireListResponseDto;
import com.example.productservice.dto.product.ProductDetailResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

public class InquireConverter {

    public static InquireListResponseDto.InquireResponse toInquireResponse(ProductInquire inquire) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(STRICT)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        InquireListResponseDto.InquireResponse result = mapper.map(inquire, InquireListResponseDto.InquireResponse.class);
        return result;
    }


    public static InquireListResponseDto.InquireAnswerResponse toInquireAnswerResponse(ProductInquireAnswer inquireAnswer) {
        if (inquireAnswer == null) {
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(STRICT)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        InquireListResponseDto.InquireAnswerResponse result =
                mapper.map(inquireAnswer, InquireListResponseDto.InquireAnswerResponse.class);

        return result;
    }
}
