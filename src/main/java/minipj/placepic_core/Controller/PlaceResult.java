package minipj.placepic_core.Controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import minipj.placepic_core.Entity.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PlaceResult {
    private Long placeId; //id
    private String placeName; //가게명
    private String startTime; //영업시작시간
    private String endTime; //영업종료시간
    private String content;//설명
    private String address; //주소
    private String detailAddress; //주소

    private String menuName;//메뉴이름
    private int price;//메뉴가격
    private String menuImage;//메뉴사진

    private String placeImage;//가게사진
}
