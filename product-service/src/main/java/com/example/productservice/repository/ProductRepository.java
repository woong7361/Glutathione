package com.example.productservice.repository;

import com.example.productservice.Entity.Product;
import com.example.productservice.repository.dsl.QueryDslProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, QueryDslProductRepository {

    @Query("SELECT pr " +
            "FROM Product pr " +
            "JOIN FETCH pr.productImages pimage " +
            "JOIN FETCH pr.productType ptype " +
            "WHERE pr.productId = :productId " +
            "AND pimage.originalName LIKE concat(:startsWith, '%') ")
    Optional<Product> findByIdWithThumbnail(Long productId, String startsWith);
}
