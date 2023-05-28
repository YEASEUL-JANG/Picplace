package minipj.placepic_core.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Controller.MenuForm;
import minipj.placepic_core.Controller.PlaceForm;
import minipj.placepic_core.Entity.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static minipj.placepic_core.Entity.QPlace.place;

@Repository
@RequiredArgsConstructor
public class PlaceRepository {

    private final EntityManager em;

    public Long save(Place place){
        em.persist(place);
        return place.getPlaceId();
    }

    public List<PlaceForm> findPlaces(PlaceSearch placeSearch){
        JPAQueryFactory query = new JPAQueryFactory(em);
        QPlace place = QPlace.place;
        QPlacePhoto placePhoto = QPlacePhoto.placePhoto;
        QMenu menu = QMenu.menu;

        List<PlaceForm> placeForms = new ArrayList<>();

        List<Place> places = query
                .select(place)
                .from(place)
                .where(nameLike(placeSearch.getPlaceName()),
                        addressLike(placeSearch.getAddress()),
                        menuLike(placeSearch.getMenuKeyword()),
                        typeLike(placeSearch.getPlaceType()))
                .fetch();
        for(Place p : places){
            PlaceForm placeForm = new PlaceForm();
            Long id = p.getPlaceId();
            List<MenuForm> menus = getMenus(query, menu, id);
            List<String> placePhotos = getPlacePhotos(id, query,placePhoto);
            settingPlace(id, placeForm, p, menus, placePhotos);
            placeForms.add(placeForm);
        }
        return placeForms;

//    return query
//            .select(Projections.bean(PlaceResult.class,
//                    place.placeId,
//                    place.placeName,
//                    place.startTime,
//                    place.endTime,
//                    place.content,
//                    place.address.address,
//                    place.address.detailAddress,
//                    menu.menuName,
//                    menu.price,
//                    menu.menuImage,
//                    placePhoto.placeImage))
//            .from(place)
//            .join(place.placePhotos, placePhoto)
//            .join(place.menuList, menu)
//            .where(nameLike(placeSearch.getPlaceName()),
//                    addressLike(placeSearch.getAddress()),
//                    menuLike(placeSearch.getMenuKeyword()),
//                    typeLike(placeSearch.getPlaceType()))
//            .fetch();
    }

   

    private Predicate menuLike(String menuKeyword) {
        if(!StringUtils.hasText(menuKeyword)){
            return null;
        }
        return place.menuList.any().menuName.contains(menuKeyword);
    }

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

    public PlaceForm findAPlace(Long searchId) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        //placeForm 객체 준비
        PlaceForm placeForm = new PlaceForm();
        //id에 해당되는 place정보
        Place place = em.find(Place.class, searchId);
        //Q테이블 준비
        QMenu menu = QMenu.menu;
        QPlacePhoto placePhoto = QPlacePhoto.placePhoto;
        //id에 해당되는 메뉴들
        List<MenuForm> menus = getMenus(query, menu, searchId);
        //id에 해당되는 장소사진
        List<String> placePhotos = getPlacePhotos(searchId, query, placePhoto);
        //setting
        settingPlace(searchId, placeForm, place, menus, placePhotos);

        return placeForm;

    }

    private static void settingPlace(Long searchId, PlaceForm placeForm, Place place, List<MenuForm> menus, List<String> placePhotos) {
        placeForm.setPlaceId(searchId);
        placeForm.setName(place.getPlaceName());
        placeForm.setStartTime(place.getStartTime());
        placeForm.setEndTime(place.getEndTime());
        placeForm.setContent(place.getContent());
        placeForm.setPlaceType(place.getPlaceType());
        placeForm.setAddress(place.getAddress().getAddress());
        placeForm.setDetailAddress(place.getAddress().getDetailAddress());
        placeForm.setZipcode(place.getAddress().getZipcode());
        placeForm.setMenuList(menus);
        placeForm.setPlacePhotos(placePhotos);
    }

    private static List<MenuForm> getMenus(JPAQueryFactory query, QMenu menu, Long id) {
        List<MenuForm> menus = query
                .select(Projections.bean(MenuForm.class,
                        menu.menuName,
                        menu.menuImage,
                        menu.price))
                .from(menu)
                .where(QMenu.menu.place.placeId.eq(id))
                .fetch();
        return menus;
    }
    private static List<String> getPlacePhotos(Long searchId, JPAQueryFactory query, QPlacePhoto placePhoto) {
        List<String> placePhotos = query
                .select(placePhoto.placeImage)
                .from(placePhoto)
                .where(QPlacePhoto.placePhoto.place.placeId.eq(searchId))
                .fetch();
        return placePhotos;
    }
}
