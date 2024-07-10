package com.example.productservice.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductStyle is a Querydsl query type for ProductStyle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductStyle extends EntityPathBase<ProductStyle> {

    private static final long serialVersionUID = 519739574L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductStyle productStyle = new QProductStyle("productStyle");

    public final QProduct product;

    public final NumberPath<Long> productStyleId = createNumber("productStyleId", Long.class);

    public final StringPath style = createString("style");

    public QProductStyle(String variable) {
        this(ProductStyle.class, forVariable(variable), INITS);
    }

    public QProductStyle(Path<? extends ProductStyle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductStyle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductStyle(PathMetadata metadata, PathInits inits) {
        this(ProductStyle.class, metadata, inits);
    }

    public QProductStyle(Class<? extends ProductStyle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

