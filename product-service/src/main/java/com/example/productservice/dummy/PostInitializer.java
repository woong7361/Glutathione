package com.example.productservice.dummy;

import com.example.productservice.Entity.Product;
import com.example.productservice.Entity.ProductImage;
import com.example.productservice.Entity.ProductStyle;
import com.example.productservice.Entity.ProductType;
import com.example.productservice.repository.ProductImageRepository;
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
    private final ProductImageRepository productImageRepository;
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

        Product product1 = Product.builder()
                .name("test_data_1")
                .description("description1")
                .content("test Image 1 <img src=\"http://211.218.223.120:30002/product-service/products/image/2\">")
                .quantity(100)
                .unitPrice(20000)
                .build();
        product1.setProductTypeId(1L);
        product1.addStyle("HOT");
        product1.addStyle("SUMMER");
        productRepository.save(product1);

        ProductImage productImage1 = ProductImage.builder()
                .originalName("thumbnail_1.jpg")
                .physicalName("test1.jpg")
                .path("/home/")
                .build();
        productImage1.setProductId(product1.getProductId());

        ProductImage productImage2 = ProductImage.builder()
                .originalName("content_2.jpg")
                .physicalName("test2.jpg")
                .path("/home/")
                .build();
        productImage2.setProductId(product1.getProductId());
        productImageRepository.save(productImage1);
        productImageRepository.save(productImage2);


        Product product2 = Product.builder()
                .name("test_data_2")
                .description("description2")
                .content("test Image 2 <img src=\"http://211.218.223.120:30002/product-service/products/image/4\">")
                .quantity(5123)
                .unitPrice(15000)
                .build();
        product2.setProductTypeId(1L);
        product2.addStyle("COOL");
        product2.addStyle("WINTER");
        productRepository.save(product2);

        ProductImage productImage3 = ProductImage.builder()
                .originalName("thumbnail_3.jpg")
                .physicalName("test3.jpg")
                .path("/home/")
                .build();
        productImage3.setProductId(product2.getProductId());

        ProductImage productImage4 = ProductImage.builder()
                .originalName("content_4.jpg")
                .physicalName("test4.jpg")
                .path("/home/")
                .build();
        productImage4.setProductId(product2.getProductId());
        productImageRepository.save(productImage3);
        productImageRepository.save(productImage4);
    }
}
