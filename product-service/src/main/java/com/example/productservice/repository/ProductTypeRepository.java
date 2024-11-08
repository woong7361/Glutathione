package com.example.productservice.repository;

import com.example.productservice.Entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 제품 타입 repository
 */
@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}
