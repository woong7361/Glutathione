package com.example.productservice.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductType is a Querydsl query type for ProductType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductType extends EntityPathBase<ProductType> {

    private static final long serialVersionUID = 1679368085L;

    public static final QProductType productType = new QProductType("productType");

    public final NumberPath<Long> productTypeId = createNumber("productTypeId", Long.class);

    public final StringPath type = createString("type");

    public QProductType(String variable) {
        super(ProductType.class, forVariable(variable));
    }

    public QProductType(Path<? extends ProductType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductType(PathMetadata metadata) {
        super(ProductType.class, metadata);
    }

}

