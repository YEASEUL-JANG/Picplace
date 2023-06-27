package minipj.placepic_core.Controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import minipj.placepic_core.Entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter@Setter@ToString
public class PlaceForm {
    private Long placeId;

    private String name; //가게명
    private String startTime; //영업시작시간
    private String endTime; //영업종료시간
    private String content; //내용
    private PlaceType placeType; //가게타입
    private MenuType menuType; //메뉴타입
    private String lat; //위도
    private String lng; //경도

    //주소
    private String address;
    private String detailAddress;
    private String zipcode;

    private List<MenuForm> menuList = new ArrayList<>(); //메뉴리스트
    private List<String> placePhotos= new ArrayList<>(); //가게사진


    public PlaceForm(Place place){
       placeId = place.getPlaceId();
       name = place.getPlaceName();
       startTime = place.getStartTime();
       endTime = place.getEndTime();
       content = place.getContent();
       placeType = place.getPlaceType();
       menuType = place.getMenuType();
       lat = place.getLat();
       lng = place.getLng();
       address = place.getAddress().getAddress();
       detailAddress = place.getAddress().getDetailAddress();
       zipcode = place.getAddress().getZipcode();
       menuList = place.getMenuList().stream()
               .map(p -> new MenuForm(p))
               .collect(Collectors.toList());
        placePhotos = place.getPlacePhotos().stream()
                .map(p ->  p.getPlaceImage())
                .collect(Collectors.toList());
   }
}
