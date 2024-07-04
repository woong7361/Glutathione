package com.example.productservice.controller;

import com.example.productservice.dto.style.StyleCountDto;
import com.example.productservice.service.ProductStyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class productStyleController {
    public static final String STYLES_RESPONSE_KEY = "styles";

    private final ProductStyleService productStyleService;

    /**
     * 가장 많이 있는 스타일 반환
     * @return 200 ok
     */
    @GetMapping("/products/styles/most")
    public ResponseEntity<Map<String, List<StyleCountDto>>> getMostStyles() {
        List<StyleCountDto> mostStyles = productStyleService.getMostStyles();

        return ResponseEntity.ok(Map.of(STYLES_RESPONSE_KEY, mostStyles));
    }
}
