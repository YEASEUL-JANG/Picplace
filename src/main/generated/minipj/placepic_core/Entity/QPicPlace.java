package minipj.placepic_core.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPicPlace is a Querydsl query type for PicPlace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPicPlace extends EntityPathBase<PicPlace> {

    private static final long serialVersionUID = 473643512L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPicPlace picPlace = new QPicPlace("picPlace");

    public final DateTimePath<java.time.LocalDateTime> picDate = createDateTime("picDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> picplaceId = createNumber("picplaceId", Long.class);

    public final QPlace place;

    public final QUser user;

    public QPicPlace(String variable) {
        this(PicPlace.class, forVariable(variable), INITS);
    }

    public QPicPlace(Path<? extends PicPlace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPicPlace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPicPlace(PathMetadata metadata, PathInits inits) {
        this(PicPlace.class, metadata, inits);
    }

    public QPicPlace(Class<? extends PicPlace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place"), inits.get("place")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

