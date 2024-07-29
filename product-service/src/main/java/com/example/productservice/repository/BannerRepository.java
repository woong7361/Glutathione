package com.example.productservice.repository;

import com.example.productservice.Entity.Banner;
import com.example.productservice.Entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *  배너 repository
 */
@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
}
