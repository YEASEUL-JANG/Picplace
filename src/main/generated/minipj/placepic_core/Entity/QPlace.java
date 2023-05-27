package minipj.placepic_core.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlace is a Querydsl query type for Place
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlace extends EntityPathBase<Place> {

    private static final long serialVersionUID = -1929178484L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlace place = new QPlace("place");

    public final QAddress address;

    public final StringPath content = createString("content");

    public final StringPath endTime = createString("endTime");

    public final ListPath<Menu, QMenu> menuList = this.<Menu, QMenu>createList("menuList", Menu.class, QMenu.class, PathInits.DIRECT2);

    public final ListPath<PicPlace, QPicPlace> picPlaces = this.<PicPlace, QPicPlace>createList("picPlaces", PicPlace.class, QPicPlace.class, PathInits.DIRECT2);

    public final NumberPath<Long> placeId = createNumber("placeId", Long.class);

    public final StringPath placeName = createString("placeName");

    public final ListPath<PlacePhoto, QPlacePhoto> placePhotos = this.<PlacePhoto, QPlacePhoto>createList("placePhotos", PlacePhoto.class, QPlacePhoto.class, PathInits.DIRECT2);

    public final EnumPath<PlaceType> placeType = createEnum("placeType", PlaceType.class);

    public final StringPath startTime = createString("startTime");

    public QPlace(String variable) {
        this(Place.class, forVariable(variable), INITS);
    }

    public QPlace(Path<? extends Place> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlace(PathMetadata metadata, PathInits inits) {
        this(Place.class, metadata, inits);
    }

    public QPlace(Class<? extends Place> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
    }

}

