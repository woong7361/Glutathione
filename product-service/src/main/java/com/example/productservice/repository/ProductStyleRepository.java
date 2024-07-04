package com.example.productservice.repository;

import com.example.productservice.Entity.ProductStyle;
import com.example.productservice.dto.style.StyleCountDto;
import com.example.productservice.repository.dsl.QueryDslProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductStyleRepository extends JpaRepository<ProductStyle, Long>, QueryDslProductRepository {

    @Query("SELECT ps.style as style, COUNT(ps) as count " +
            "FROM ProductStyle ps " +
            "GROUP BY ps.style " +
            "ORDER BY count DESC " +
            "LIMIT 10")
    List<StyleCountDto> findMostCommonStyle();

}
