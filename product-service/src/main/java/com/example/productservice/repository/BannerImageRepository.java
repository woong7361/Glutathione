package com.example.productservice.repository;

import com.example.productservice.Entity.Banner;
import com.example.productservice.Entity.BannerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  배너 repository
 */
@Repository
public interface BannerImageRepository extends JpaRepository<BannerImage, Long> {
}
