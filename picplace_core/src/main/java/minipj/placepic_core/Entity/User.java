package minipj.placepic_core.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    //username : 로그인 아이디
    @Column(name="user_name",nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Embedded
    private Address address;

    private LocalDateTime joinDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<PicPlace> picPlaces = new ArrayList<>();

    //Transient: 데이터베이스에 저장되지 않고 일회성으로만 사용된다.
    @Transient
    private String token;
}
