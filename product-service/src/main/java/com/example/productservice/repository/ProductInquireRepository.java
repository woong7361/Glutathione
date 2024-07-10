package com.example.productservice.repository;

import com.example.productservice.Entity.ProductInquire;
import com.example.productservice.Entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductInquireRepository extends JpaRepository<ProductInquire, Long> {
    Optional<ProductInquire> findByProductInquireIdAndMemberId(Long inquireId, Long memberId);
}
