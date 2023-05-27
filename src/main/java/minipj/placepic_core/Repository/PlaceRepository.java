package minipj.placepic_core.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Controller.PlaceResult;
import minipj.placepic_core.Entity.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static minipj.placepic_core.Entity.QMenu.menu;
import static minipj.placepic_core.Entity.QPlace.place;

@Repository
@RequiredArgsConstructor
public class PlaceRepository {

    private final EntityManager em;

    public Long save(Place place){
        em.persist(place);
        return place.getPlaceId();
    }
    public Place find(Long id){
        return em.find(Place.class,id);
    }
    public List<PlaceResult> findPlaces(PlaceSearch placeSearch){
        JPAQueryFactory query = new JPAQueryFactory(em);
        QPlace place = QPlace.place;
        QPlacePhoto placePhoto = QPlacePhoto.placePhoto;
        QMenu menu = QMenu.menu;

    return query
            .select(Projections.bean(PlaceResult.class,
                    place.placeId,
                    place.placeName,
                    place.startTime,
                    place.endTime,
                    place.content,
                    place.address.address,
                    place.address.detailAddress,
                    menu.menuName,
                    menu.price,
                    menu.menuImage,
                    placePhoto.placeImage))
            .from(place)
            .join(place.placePhotos, placePhoto)
            .join(place.menuList, menu)
            .where(nameLike(placeSearch.getPlaceName()),
                    addressLike(placeSearch.getAddress()),
                    menuLike(placeSearch.getMenuKeyword()),
                    typeLike(placeSearch.getPlaceType()))
            //.orderBy(orderList(placeSearch.getOrderType()))
            .fetch()
            ;
    }

    private Predicate menuLike(String menuKeyword) {
        if(!StringUtils.hasText(menuKeyword)){
            return null;
        }
        return menu.menuName.contains(menuKeyword);
    }


//

    private Predicate typeLike(String placeType) {
        if(!StringUtils.hasText(placeType)){
            return null;
        }
        return place.placeType.eq(PlaceType.valueOf(placeType));
    }

    private Predicate addressLike(String address) {
        if(!StringUtils.hasText(address)){
            return null;
        }
        return place.address.address.contains(address);
    }

    private Predicate nameLike(String placeName) {
        if(!StringUtils.hasText(placeName)){
            return null;
        }
        return place.placeName.contains(placeName);
    }
}
