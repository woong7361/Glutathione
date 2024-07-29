package com.example.productservice.repository.dsl;

import com.example.productservice.Entity.*;
import com.example.productservice.converter.ProductConverter;
import com.example.productservice.dto.inquire.InquireCountDto;
import com.example.productservice.dto.product.ProductFavoriteDto;
import com.example.productservice.dto.product.ProductSearchRequestDto;
import com.example.productservice.dto.product.ProductSearchResponseDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.productservice.Entity.QProduct.product;
import static com.example.productservice.Entity.QProductFavorite.productFavorite;
import static com.example.productservice.Entity.QProductImage.productImage;
import static com.example.productservice.Entity.QProductInquire.productInquire;
import static com.example.productservice.Entity.QProductStyle.productStyle;
import static com.querydsl.core.types.ExpressionUtils.count;

@RequiredArgsConstructor
public class QueryDslProductRepositoryImpl implements QueryDslProductRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<ProductSearchResponseDto> search(ProductSearchRequestDto searchRequestDto, Long memberId) {
        List<Product> productsWithStyles = queryFactory
                .select(product)
                .from(product)
                .leftJoin(product.productStyles, productStyle).fetchJoin()
                .where(searchCondition(searchRequestDto))
                .distinct()
                .orderBy(orderCondition(searchRequestDto))
                .offset(searchRequestDto.getOffset())
                .limit(searchRequestDto.getSize())
                .fetch();

        List<Long> ids = productsWithStyles.stream()
                .map(Product::getProductId)
                .collect(Collectors.toList());

        List<ProductFavoriteDto> favoriteList = queryFactory
                .select(Projections.fields(ProductFavoriteDto.class,
                        product.productId,
                        ExpressionUtils.as(
                                JPAExpressions.select(count(productFavorite.productFavoriteId))
                                        .from(productFavorite)
                                        .where(productFavorite.product.productId.eq(product.productId)),
                                "favoriteCount"),
                        ExpressionUtils.as(
                                JPAExpressions.select()
                                        .from(productFavorite)
                                        .where(productFavorite.product.productId.eq(product.productId)
                                                .and(productFavorite.memberId.eq(memberId)))
                                        .exists(),
                                "isFavor")
                        ))
                .from(product)
                .distinct()
                .where(product.productId.in(ids))
                .fetch();

        List<InquireCountDto> inquireCountList = queryFactory
                .select(Projections.fields(InquireCountDto.class,
                        product.productId,
                        ExpressionUtils.as(
                                JPAExpressions.select(count(productInquire.productInquireId))
                                        .from(productInquire)
                                        .where(productInquire.product.productId.eq(product.productId)),
                                "inquireCount")
                ))
                .from(product)
                .distinct()
                .where(product.productId.in(ids))
                .fetch();


        List<Product> productsWithThumbnail = queryFactory
                .selectFrom(product)
                .leftJoin(product.productImages, productImage).fetchJoin()
                .distinct()
                .where(product.productId.in(ids)
                        .and(productImage.originalName.like("thumbnail_%")))
                .fetch();

        List<ProductSearchResponseDto> result = productsWithStyles.stream()
                .map(pr -> ProductConverter.toProductSearchResponseDto(pr))
                .toList();

        Map<Long, ProductFavoriteDto> favoriteMap = favoriteList.stream()
                .collect(Collectors.toMap(fl -> fl.getProductId(), fl -> fl));
        Map<Long, InquireCountDto> inquireMap = inquireCountList.stream()
                .collect(Collectors.toMap(inq -> inq.getProductId(), inq -> inq));
        Map<Long, Product> thumbnailMap = productsWithThumbnail.stream()
                .collect(Collectors.toMap(pr -> pr.getProductId(), pr -> pr));

        result.forEach(res -> {
            res.setThumbnail(thumbnailMap.get(res.getProductId()).getProductImages().get(0));
            res.setFavorCount(favoriteMap.get(res.getProductId()).getFavoriteCount());
            res.setIsFavor(favoriteMap.get(res.getProductId()).getIsFavor());
            res.setInquireCount(inquireMap.get(res.getProductId()).getInquireCount());
        });

        return result;
    }

    private OrderSpecifier<?> orderCondition(ProductSearchRequestDto searchRequestDto) {
        Path path = product.createdAt;
        Order order = Order.DESC;

        if (searchRequestDto.getSortElement() == null || searchRequestDto.getSortType() == null) {
            return new OrderSpecifier<>(order, path);
        }

        switch (searchRequestDto.getSortElement()) {
            case "createdAt":
                path = product.createdAt;
                break;
            case "unitPrice":
                path = product.unitPrice;
                break;
            case "name":
                path = product.name;
                break;
            case "quantity":
                path = product.quantity;
                break;
        }

        switch (searchRequestDto.getSortType()) {
            case "ASC":
                order = Order.ASC;
                break;
            case "DESC":
                order = Order.DESC;
                break;
        }

        return new OrderSpecifier<>(order, path);
    }

    private BooleanBuilder searchCondition(ProductSearchRequestDto searchRequestDto) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        return booleanBuilder
                .and(productNameLike(searchRequestDto.getKeyword()))
                .and(productTypeEqual(searchRequestDto.getType()))
                .and(productStyleHave(searchRequestDto.getStyle()));
    }

    private BooleanExpression productStyleHave(String style) {
        return StringUtils.hasText(style) ? productStyle.style.eq(style) : null;

    }

    private BooleanExpression productTypeEqual(String type) {
        return StringUtils.hasText(type) ? product.productType.type.eq(type) : null;
    }

    private BooleanExpression productNameLike(String keyword) {
        return StringUtils.hasText(keyword) ? product.name.contains(keyword) : null;
    }

    private BooleanExpression thumbnail() {
        return product.productImages.any().originalName.like("thumbnail_%");
    }

}
