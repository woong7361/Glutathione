package com.example.productservice.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductImage is a Querydsl query type for ProductImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductImage extends EntityPathBase<ProductImage> {

    private static final long serialVersionUID = 510272608L;

    public static final QProductImage productImage = new QProductImage("productImage");

    public final StringPath originalName = createString("originalName");

    public final StringPath path = createString("path");

    public final StringPath physicalName = createString("physicalName");

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> productImageId = createNumber("productImageId", Long.class);

    public QProductImage(String variable) {
        super(ProductImage.class, forVariable(variable));
    }

    public QProductImage(Path<? extends ProductImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductImage(PathMetadata metadata) {
        super(ProductImage.class, metadata);
    }

}

