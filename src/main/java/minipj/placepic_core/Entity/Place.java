package minipj.placepic_core.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="place_id")
    private Long id;

    @Column(name="place_name")
    private String name;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private PlaceType placeType;

    //값타입을 컬렉션으로 사용하기보다 엔티티로 만들어서 일대다 관계로 설정
    @OneToMany(mappedBy = "id")
    private List<MenuEntity> menuList=new ArrayList<>();

    @OneToMany(mappedBy = "place")
    private List<PicPlace> picUserList = new ArrayList<>();

}
