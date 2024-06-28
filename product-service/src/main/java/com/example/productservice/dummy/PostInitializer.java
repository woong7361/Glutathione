package com.example.productservice.dummy;

import com.example.productservice.Entity.Product;
import com.example.productservice.Entity.ProductStyle;
import com.example.productservice.Entity.ProductType;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 더미 데이터를 생성해주는 Initializer
 * TODO 배포 환경에서는 없어져야한다.
 */
@Component
@RequiredArgsConstructor
public class PostInitializer {

    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;

    /**
     * 관리자 초기값 생성
     */
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        ProductType shirtType = ProductType.builder()
                .type("shirt")
                .build();

        productTypeRepository.save(shirtType);
    }
}
