package minipj.placepic_core.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minipj.placepic_core.Entity.*;
import minipj.placepic_core.Entity.Form.MenuForm;
import minipj.placepic_core.Entity.Form.PlaceForm;
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

        List<PlaceForm> placeForms = new ArrayList<>();

        List<Place> places = query
                .select(place)
                .from(place)
                .where(nameLike(placeSearch.getPlaceName()),
                        addressLike(placeSearch.getAddress()),
                        menuLike(placeSearch.getMenuKeyword()),
                        placeTypeLike(placeSearch.getPlaceType()),
                        menuTypeLike(placeSearch.getMenuType()))
                .offset((placeSearch.getPageNum() -1) * 9)
                .limit(9)
                .fetch();
        for(Place p : places){
            PlaceForm placeForm = new PlaceForm(p);
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
        Place place = em.find(Place.class, searchId);
        PlaceForm placeForm = new PlaceForm(place);
        return placeForm;

    }

    public void deletePlace(Long id) {
        em.createQuery("delete from Place p where p.placeId=:placeId")
                .setParameter("placeId",id)
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
                PlaceForm placeForm = new PlaceForm(pplace);
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

    public void deleteMenu(Long placeId) {
        int result = em.createQuery("delete from Menu m where m.place.placeId=:placeId")
                .setParameter("placeId",placeId)
                .executeUpdate();
        log.info("삭제된 메뉴 수 : {}",result);
    }

    public void deletePlacePhoto(Long placeId) {
        int result = em.createQuery("delete from PlacePhoto p where p.place.placeId=:placeId")
                .setParameter("placeId",placeId)
                .executeUpdate();
        log.info("삭제된 포토 수 : {}",result);
    }
}
