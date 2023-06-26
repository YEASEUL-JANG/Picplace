package minipj.placepic_core.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minipj.placepic_core.Controller.MenuForm;
import minipj.placepic_core.Controller.PlaceForm;
import minipj.placepic_core.Entity.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static minipj.placepic_core.Entity.QPlace.place;

@Repository
@Slf4j
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
                        placeTypeLike(placeSearch.getPlaceType()),
                        menuTypeLike(placeSearch.getMenuType()))
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
    }

    private Predicate menuLike(String menuKeyword) {
        if(!StringUtils.hasText(menuKeyword)){
            return null;
        }
        return place.menuList.any().menuName.contains(menuKeyword);
    }

    private Predicate placeTypeLike(String placeType) {
        if(!StringUtils.hasText(placeType)){
            return null;
        }
        return place.placeType.eq(PlaceType.valueOf(placeType));
    }

    private Predicate menuTypeLike(String menuType) {
        if(!StringUtils.hasText(menuType)){
            return null;
        }
        return place.menuType.eq(MenuType.valueOf(menuType));
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
        placeForm.setMenuType(place.getMenuType());
        placeForm.setAddress(place.getAddress().getAddress());
        placeForm.setLat(place.getLat());
        placeForm.setLng(place.getLng());
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

    public void deletePlace(Long id) {
        em.createQuery("delete from User u where u.userId=:userid")
                .setParameter("userid",id)
                .executeUpdate();
    }

    public Long editPlace(PlaceForm form) {
        Place findplace = em.find(Place.class, form.getPlaceId());
        findplace.setPlaceName(form.getName());
        findplace.setPlaceType(form.getPlaceType());
        findplace.setMenuType(form.getMenuType());
        findplace.setContent(form.getContent());
        Address newAddress = new Address(form.getAddress(),form.getDetailAddress(), form.getZipcode());
        findplace.setAddress(newAddress);
        findplace.setStartTime(form.getStartTime());
        findplace.setEndTime(form.getEndTime());
        //기존 메뉴 삭제
        findplace.setMenuList(null);
        //메뉴 변경 진행
        List<MenuForm> menuList = form.getMenuList();
        for(MenuForm m : menuList){
            findplace.addMenu(m);
        }
        //기존 사진 삭제
        findplace.setPlacePhotos(null);
        //장소 사진 변경
        for(String a : form.getPlacePhotos()){
            findplace.addPhoto(a);
        }

        return findplace.getPlaceId();
    }

    public Long placePic(Long placeId, User picuser) {
        Place place = em.find(Place.class, placeId);
        place.addPicplace(picuser);
        return place.getPlaceId();
    }

    public boolean checkduplPic(Long placeId) {
        Place place = em.find(Place.class,placeId);
        if(place.getPicPlaces().size()>0){
            return true;
        }else{
            return false;
        }
    }

    public List<PlaceForm> findPicPlaces(Long userId) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QPlacePhoto placePhoto = QPlacePhoto.placePhoto;
        QMenu menu = QMenu.menu;
        QPicPlace picPlace = QPicPlace.picPlace;

        List<PlaceForm> placeForms = new ArrayList<>();

        //userId가 찜한 placeId list조회
        List<Long> placeids = query
                .select(picPlace.place.placeId)
                .from(picPlace)
                .where(picPlace.user.userId.eq(userId))
                .fetch();
        //placeid값으로 일치하는 place정보를 placeForm에 반환
        for (Long placeid : placeids) {
            Place pplace = em.find(Place.class, placeid);
                PlaceForm placeForm = new PlaceForm();
                Long id = pplace.getPlaceId();
                List<MenuForm> menus = getMenus(query, menu, id);
                List<String> placePhotos = getPlacePhotos(id, query,placePhoto);
                settingPlace(id, placeForm, pplace, menus, placePhotos);
                placeForms.add(placeForm);
        }
        return placeForms;
    }

    public void deletePlacePic(Long userId, Long placeId) {
        int result = em.createQuery("delete from PicPlace p where p.user.userId=:userid" +
                        " and p.place.placeId=:placeId")
                .setParameter("userid",userId)
                .setParameter("placeId",placeId)
                .executeUpdate();
        log.info("삭제된 쿼리 수 : {}",result);
    }

    public List<PicPlace> findAllPicPlaces() {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QPicPlace picPlace = QPicPlace.picPlace;
        List<PicPlace> picPlaceList =
                query.select(picPlace)
                        .from(picPlace)
                        .fetch();
        return picPlaceList;
    }
}
