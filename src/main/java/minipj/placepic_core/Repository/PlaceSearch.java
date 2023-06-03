package minipj.placepic_core.Repository;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import minipj.placepic_core.Entity.PlaceType;

@Getter@Setter@ToString
public class PlaceSearch {

    private String placeName;//가게검색
    private String menuKeyword; //메뉴검색
    private String address; //주소검색
    private String placeType; //장소카테고리
    private String menuType; //메뉴카테고리
}
