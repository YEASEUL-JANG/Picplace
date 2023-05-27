package minipj.placepic_core.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import minipj.placepic_core.Controller.MenuForm;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="place_id")
    private Long placeId; //id

    @Column(name="place_name")
    private String placeName; //가게명

    private String startTime; //영업시작시간
    private String endTime; //영업종료시간
    @Lob
    private String content;
    @Embedded
    private Address address; //주소

    @Enumerated(EnumType.STRING)
    private PlaceType placeType; //가게타입

    //CascadeType.ALL : place를 persist 하면 menuList도 모두 persist되게한다.
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL) //메뉴리스트
    private List<Menu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PicPlace> picPlaces= new ArrayList<>(); //찜테이블 객체

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlacePhoto> placePhotos= new ArrayList<>();

    //연관관계 편의 메서드
    public void addMenu(MenuForm form) {
        Menu menu = new Menu();
        menu.setMenuName(form.getMuneName());
        menu.setPrice(form.getPrice());
        menu.setMenuImage(form.getMenuImage());
        menu.setPlace(this);
        menuList.add(menu);
    }
    public void addPhoto(String photo){
        PlacePhoto placePhoto = new PlacePhoto();
        placePhoto.setPlaceImage(photo);
        placePhoto.setPlace(this);
        placePhotos.add(placePhoto);
    }

    //생성메서드
    public static Place createPlace(
            String placeName,
            String startTime,
            String endTime,
            String content,
            Address address,
            PlaceType placeType,
            List<String> placePhotos,
            List<MenuForm> menuList){
        Place place = new Place();
        for(String photo : placePhotos){
            place.addPhoto(photo);
        }
        for(MenuForm m : menuList){
            place.addMenu(m);
        }
        place.setPlaceName(placeName);
        place.setStartTime(startTime);
        place.setEndTime(endTime);
        place.setContent(content);
        place.setAddress(address);
        place.setPlaceType(placeType);
        return place;
    }



}
