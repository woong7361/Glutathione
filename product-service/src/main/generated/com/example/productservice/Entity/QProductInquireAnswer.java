package com.example.productservice.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductInquireAnswer is a Querydsl query type for ProductInquireAnswer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductInquireAnswer extends EntityPathBase<ProductInquireAnswer> {

    private static final long serialVersionUID = -418576042L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductInquireAnswer productInquireAnswer = new QProductInquireAnswer("productInquireAnswer");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final QProductInquire productInquire;

    public final NumberPath<Long> productInquireAnswerId = createNumber("productInquireAnswerId", Long.class);

    public QProductInquireAnswer(String variable) {
        this(ProductInquireAnswer.class, forVariable(variable), INITS);
    }

    public QProductInquireAnswer(Path<? extends ProductInquireAnswer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductInquireAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductInquireAnswer(PathMetadata metadata, PathInits inits) {
        this(ProductInquireAnswer.class, metadata, inits);
    }

    public QProductInquireAnswer(Class<? extends ProductInquireAnswer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productInquire = inits.isInitialized("productInquire") ? new QProductInquire(forProperty("productInquire"), inits.get("productInquire")) : null;
    }

}

