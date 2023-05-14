package minipj.placepic_core.Entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String city;
    private String address1;
    private String address2;
    private String street;
    private String zipcode;
}
