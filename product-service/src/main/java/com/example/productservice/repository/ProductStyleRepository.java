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

    @Query(value = "SELECT product_style.style as style, COUNT(product_style.product_style_id) as count " +
            "FROM product_style " +
            "GROUP BY product_style.style " +
            "ORDER BY count DESC " +
            "LIMIT :size",
            nativeQuery = true)
    List<StyleCountDto> findMostCommonStyle(Integer size);

    void deleteByProductProductId(Long productId);

//    @Query(value = "SELECT ranked.style, ranked.productId, ranked.name, ranked.style_count " +
//            "FROM (" +
//            "    SELECT ps.style as style, p.productId as productId, p.name as name, " +
//            "           COUNT(*) OVER (PARTITION BY ps.style) as style_count, " +
//            "           ROW_NUMBER() OVER (PARTITION BY ps.style ORDER BY p.productId) as row_num " +
//            "    FROM ProductStyle ps " +
//            "    JOIN Product p ON ps.product.productId = p.productId " +
//            ") ranked " +
//            "WHERE ranked.row_num <= 5 " +
//            "  AND ranked.style IN (" +
//            "    SELECT top_styles.style " +
//            "    FROM (" +
//            "        SELECT ps.style AS style, COUNT(*) as count" +
//            "        FROM ProductStyle ps" +
//            "        GROUP BY ps.style " +
//            "        ORDER BY count DESC " +
//            "        LIMIT 5" +
//            "    ) top_styles" +
//            ") " +
//            "ORDER BY ranked.style_count DESC, ranked.style")
//    List<Object[]> findTop5StylesWithTop5Products();

}
