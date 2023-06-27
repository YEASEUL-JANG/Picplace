package minipj.placepic_core.Service;

import minipj.placepic_core.Controller.MenuForm;
import minipj.placepic_core.Controller.PlaceForm;
import minipj.placepic_core.Entity.PlaceType;
import minipj.placepic_core.Repository.PlaceRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class PlaceServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    PlaceService placeService;
    @Test
    @Rollback(value = false)
    void 장소등록() {
        //PlaceForm form = new PlaceForm();
//        form.setName("국밥집");
//        form.setPlaceType(PlaceType.RESTAURANT);
//        form.setAddress("서울 강서구 공항대로");
//        form.setPlacePhotos(Arrays.asList("포토1","포토2"));
        List<MenuForm> menuFormList = new ArrayList<>();
        menuFormList.add(new MenuForm("국밥1",5000,"국밥1.png"));
        menuFormList.add(new MenuForm("국밥2",5000,"국밥2.png"));
        menuFormList.add(new MenuForm("국밥3",5000,"국밥3.png"));
       // form.setMenuList(menuFormList);
        //장소 등록
       // Long placeId = placeService.createPlace(form);
       // PlaceForm place = placeRepository.findAPlace(placeId);
        //동일 아이디 확인
      //  Assert.assertEquals("장소등록 id확인",placeId,place.getPlaceId());
    }

    @Test
    void 장소전체목록() {
    }

    @Test
    void 장소찾기() {
    }

//    @Test
//    void 장소삭제() throws Exception{
//        Long placeId = 장소등록();
//        placeRepository.deletePlace(placeId);
//        placeRepository.findAPlace(placeId);
//        fail("예외 발생");
//    }
}