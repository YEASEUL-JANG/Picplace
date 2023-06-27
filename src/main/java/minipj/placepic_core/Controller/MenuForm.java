package minipj.placepic_core.Controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import minipj.placepic_core.Entity.Menu;
import minipj.placepic_core.Entity.Place;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Getter
@Setter
@ToString
public class MenuForm {

    private String menuName;
    private int price;
    private String menuImage;

    public MenuForm(Menu menu) {
        menuName = menu.getMenuName();
        price = menu.getPrice();
        menuImage = menu.getMenuImage();
    }

    public MenuForm(String menuName, int price, String menuImage) {
        this.menuName = menuName;
        this.price = price;
        this.menuImage = menuImage;
    }
}
