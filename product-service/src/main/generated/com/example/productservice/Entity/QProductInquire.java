package com.example.productservice.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductInquire is a Querydsl query type for ProductInquire
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductInquire extends EntityPathBase<ProductInquire> {

    private static final long serialVersionUID = 789534584L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductInquire productInquire = new QProductInquire("productInquire");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final QProduct product;

    public final QProductInquireAnswer productInquireAnswer;

    public final NumberPath<Long> productInquireId = createNumber("productInquireId", Long.class);

    public QProductInquire(String variable) {
        this(ProductInquire.class, forVariable(variable), INITS);
    }

    public QProductInquire(Path<? extends ProductInquire> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductInquire(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductInquire(PathMetadata metadata, PathInits inits) {
        this(ProductInquire.class, metadata, inits);
    }

    public QProductInquire(Class<? extends ProductInquire> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.productInquireAnswer = inits.isInitialized("productInquireAnswer") ? new QProductInquireAnswer(forProperty("productInquireAnswer"), inits.get("productInquireAnswer")) : null;
    }

}

