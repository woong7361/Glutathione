package com.example.productservice.service;

import com.example.productservice.Entity.Product;
import com.example.productservice.converter.ProductConverter;
import com.example.productservice.dto.ProductCreateRequestDto;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(ProductCreateRequestDto createRequestDto) {
        Product product = ProductConverter.createConverter(createRequestDto);

        productRepository.save(product);

        return product;
    }
}
