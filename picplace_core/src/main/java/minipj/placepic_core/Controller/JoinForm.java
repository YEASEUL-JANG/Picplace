package minipj.placepic_core.Controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinForm {

    private String username;
    private String password;

    private String address;
    private String detailAddress;
    private String zipcode;

}
