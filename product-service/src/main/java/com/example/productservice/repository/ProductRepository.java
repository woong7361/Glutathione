package com.example.productservice.repository;

import com.example.productservice.Entity.Product;
import com.example.productservice.dto.product.ProductFavoriteDto;
import com.example.productservice.dto.product.FavoriteProductResponseDto;
import com.example.productservice.repository.dsl.QueryDslProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 제품 repository
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, QueryDslProductRepository {

    /**
     * 썸네일을 포함한 제품 조회
     * @param productId 제품 식별자
     * @param startsWith 썸네일 시작 문자열
     * @return
     */
    @Query("SELECT pr " +
            "FROM Product pr " +
            "JOIN FETCH pr.productImages pimage " +
            "JOIN FETCH pr.productType ptype " +
            "WHERE pr.productId = :productId " +
            "AND pimage.originalName LIKE concat(:startsWith, '%') ")
    Optional<Product> findByIdWithThumbnail(Long productId, String startsWith);

    /**
     * 제품 좋아요 개수 카운트
     * @param productId 제품 식별자
     * @param memberId 회원 식별자
     * @return 좋아요 개수
     */
    @Query("SELECT NEW com.example.productservice.dto.product.ProductFavoriteDto( " +
            "   pr.productId, " +
            "   (SELECT count(pf.productFavoriteId) " +
            "       FROM ProductFavorite pf " +
            "       WHERE pf.product.productId = :productId), " +
            "   (SELECT count(pf.productFavoriteId) > 0 " +
            "       FROM ProductFavorite pf " +
            "       WHERE pf.product.productId = :productId AND pf.memberId = :memberId)) " +
            "FROM Product pr " +
            "WHERE pr.productId = :productId ")
    ProductFavoriteDto findFavoriteCountById(Long productId, Long memberId);

    /**
     * 회원이 좋아요를 누른 제품 리스트 반환
     * @param memberId 회원 식별자
     * @param pageable 페이지네이션 요청 인자
     * @return 제품 리스트
     */
    @Query("SELECT" +
            "   pr.productId AS productId, pr.name AS name, pr.description AS description, " +
            "   pr.unitPrice AS unitPrice, pr.quantity AS quantity, " +
            "   ptype AS productType, " +
            "   pf, " +
            "   (SELECT GROUP_CONCAT(ps.style) " +
            "       FROM ProductStyle ps " +
            "       WHERE ps.product.productId = pr.productId) AS productStylesString, " +
            "   (SELECT pi.productImageId " +
            "       FROM ProductImage pi" +
            "       WHERE pi.product.productId = pr.productId AND pi.originalName LIKE 'thumbnail_%') AS thumbnailImageId, " +
            "   (SELECT count(pf.productFavoriteId) " +
            "       FROM ProductFavorite pf " +
            "       WHERE pf.product.productId = pr.productId) AS favorCount, " +
            "   (SELECT count(pf.productFavoriteId) > 0 " +
            "       FROM ProductFavorite pf " +
            "       WHERE pf.product.productId = pr.productId AND pf.memberId = :memberId) AS isFavor " +
            "FROM ProductFavorite pf " +
            "   JOIN FETCH pf.product pr " +
            "   JOIN FETCH pr.productType ptype " +
            "WHERE pf.memberId = :memberId ")
    List<FavoriteProductResponseDto> findProductByMemberFavorite(Long memberId, Pageable pageable);

    /**
     * 제품 수량 변경
     * @param productId 제품 식별자
     * @param quantity 제품 수량
     */
    @Modifying()
    @Query("UPDATE Product p " +
            "SET p.quantity = p.quantity - :quantity " +
            "WHERE p.productId = :productId")
    void reduceQuantity(Long productId, Long quantity);
}
