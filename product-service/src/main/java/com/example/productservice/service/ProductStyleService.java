package com.example.productservice.service;

import com.example.productservice.dto.style.StyleCountDto;
import com.example.productservice.repository.ProductStyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 제품 스타일 서비스 로직
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductStyleService {

    private final ProductStyleRepository productStyleRepository;

    /**
     * 가장 많이 있는 스타일 반환
     * @return list styles and count
     */
    public List<StyleCountDto> getMostStyles(Integer size) {
        return productStyleRepository.findMostCommonStyle(size);
    }
}
