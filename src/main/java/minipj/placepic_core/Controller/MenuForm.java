package minipj.placepic_core.Controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import minipj.placepic_core.Entity.Place;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Getter
@Setter
@ToString
public class MenuForm {

    private String muneName;
    private int price;
    private String menuImage;
}
