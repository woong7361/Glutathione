package com.example.productservice.repository;

import com.example.productservice.Entity.ProductInquireAnswer;
import com.example.productservice.Entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInquireAnswerRepository extends JpaRepository<ProductInquireAnswer, Long> {
}
