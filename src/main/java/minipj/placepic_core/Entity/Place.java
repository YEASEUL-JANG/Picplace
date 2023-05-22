package minipj.placepic_core.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private Long id; //id

    @Column(name="place_name")
    private String name; //가게명

    private String startTime; //영업시작시간
    private String endTime; //영업종료시간
    @Lob
    private String content;
    @Embedded
    private Address address; //주소

    @Enumerated(EnumType.STRING)
    private PlaceType placeType; //가게타입

    @OneToMany(mappedBy = "place") //메뉴리스트
    private List<Menu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "place")
    private List<PicPlace> picPlaces= new ArrayList<>(); //찜테이블 객체

    @OneToMany(mappedBy = "place")
    private List<PlacePhoto> placePhotos= new ArrayList<>();

}
