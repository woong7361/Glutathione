package com.example.productservice.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBannerImage is a Querydsl query type for BannerImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBannerImage extends EntityPathBase<BannerImage> {

    private static final long serialVersionUID = 1243624539L;

    public static final QBannerImage bannerImage = new QBannerImage("bannerImage");

    public final NumberPath<Long> bannerImageId = createNumber("bannerImageId", Long.class);

    public final StringPath originalName = createString("originalName");

    public final StringPath path = createString("path");

    public final StringPath physicalName = createString("physicalName");

    public QBannerImage(String variable) {
        super(BannerImage.class, forVariable(variable));
    }

    public QBannerImage(Path<? extends BannerImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBannerImage(PathMetadata metadata) {
        super(BannerImage.class, metadata);
    }

}

