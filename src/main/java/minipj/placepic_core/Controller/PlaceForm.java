package minipj.placepic_core.Controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import minipj.placepic_core.Entity.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter@ToString
public class PlaceForm {

    private String name; //가게명
    private String startTime; //영업시작시간
    private String endTime; //영업종료시간
    private String content; //내용
    private PlaceType placeType; //가게타입

    //주소
    private String address;
    private String detailAddress;
    private String zipcode;

    private List<MenuForm> menuList = new ArrayList<>(); //메뉴리스트
    private List<String> placePhotos= new ArrayList<>(); //가게사진
}
