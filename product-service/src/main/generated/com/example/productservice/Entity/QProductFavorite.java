package com.example.productservice.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductFavorite is a Querydsl query type for ProductFavorite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductFavorite extends EntityPathBase<ProductFavorite> {

    private static final long serialVersionUID = -742476681L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductFavorite productFavorite = new QProductFavorite("productFavorite");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final QProduct product;

    public final NumberPath<Long> productFavoriteId = createNumber("productFavoriteId", Long.class);

    public QProductFavorite(String variable) {
        this(ProductFavorite.class, forVariable(variable), INITS);
    }

    public QProductFavorite(Path<? extends ProductFavorite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductFavorite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductFavorite(PathMetadata metadata, PathInits inits) {
        this(ProductFavorite.class, metadata, inits);
    }

    public QProductFavorite(Class<? extends ProductFavorite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

