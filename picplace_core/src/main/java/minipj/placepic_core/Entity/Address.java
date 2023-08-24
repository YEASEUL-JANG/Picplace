package minipj.placepic_core.Entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String address;
    private String detailAddress; //나머지 입력주소
    private String zipcode;

    public Address(String address, String detailAddress, String zipcode) {
        this.address = address;
        this.detailAddress = detailAddress;
        this.zipcode = zipcode;
    }

    protected Address() {

    }
}
