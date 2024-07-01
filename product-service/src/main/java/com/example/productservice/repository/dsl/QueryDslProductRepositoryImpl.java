package com.example.productservice.repository.dsl;

import com.example.productservice.Entity.Product;
import com.example.productservice.Entity.ProductStyle;
import com.example.productservice.Entity.QProduct;
import com.example.productservice.dto.product.ProductSearchRequestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.productservice.Entity.QProduct.product;
import static com.example.productservice.Entity.QProductStyle.productStyle;

@RequiredArgsConstructor
public class QueryDslProductRepositoryImpl implements QueryDslProductRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findProducts() {
        QProduct r = product;
        return queryFactory.selectFrom(r).where(r.productId.eq(1L)).fetch();
    }

    @Override
    public List<Product> search(ProductSearchRequestDto searchRequestDto) {
        return queryFactory
                .selectFrom(product)
                .leftJoin(product.productStyles).fetchJoin()
                .where(searchCondition(searchRequestDto))
                .orderBy(orderCondition(searchRequestDto))
                .offset(searchRequestDto.getOffset())
                .limit(searchRequestDto.getSize())
                .fetch();
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
        return StringUtils.hasText(style) ? product.productStyles.any().style.eq(style) : null;

    }

    private BooleanExpression productTypeEqual(String type) {
        return StringUtils.hasText(type) ? product.productType.type.eq(type) : null;
    }

    private BooleanExpression productNameLike(String keyword) {
        return StringUtils.hasText(keyword) ? product.name.contains(keyword) : null;
    }

}
