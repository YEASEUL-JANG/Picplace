package minipj.placepic_core.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import minipj.placepic_core.Entity.Address;
import minipj.placepic_core.Entity.PicPlace;
import minipj.placepic_core.Entity.Role;
import minipj.placepic_core.Entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class UserDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo{
        private Long userId;
        private String username;
        private String address;
        private String detailAddress;
        private String zipcode;
        private String joinDate;


        public static UserInfo toDto(User user){
            return UserInfo.builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .address(user.getAddress().getAddress())
                    .detailAddress(user.getAddress().getDetailAddress())
                    .zipcode(user.getAddress().getZipcode())
                    .joinDate(user.getJoinDate().toString())
                    .build();
        }
    }

}
