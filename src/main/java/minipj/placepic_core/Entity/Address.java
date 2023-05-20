package minipj.placepic_core.Entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String address1;
    private String address2; //나머지 입력주소
    private String zipcode;
}
