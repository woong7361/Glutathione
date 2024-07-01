package com.example.productservice.repository;

import com.example.productservice.Entity.Product;
import com.example.productservice.repository.dsl.QueryDslProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, QueryDslProductRepository {
}
