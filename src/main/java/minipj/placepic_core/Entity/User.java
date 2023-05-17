package minipj.placepic_core.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="user_name")
    private String username;

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
