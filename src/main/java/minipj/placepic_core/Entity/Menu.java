package minipj.placepic_core.Entity;

import lombok.Getter;

import javax.persistence.*;

@Embeddable
@Getter
public class Menu {

    private Long menuId;
    private String menuName;
    private int price;
    private String image;
}
