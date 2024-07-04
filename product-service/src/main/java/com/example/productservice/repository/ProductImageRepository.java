package com.example.productservice.repository;

import com.example.productservice.Entity.ProductFavorite;
import com.example.productservice.Entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *  제품 이미지 repository
 */
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
