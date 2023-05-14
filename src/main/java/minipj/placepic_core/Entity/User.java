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
    private String name;
    @Embedded
    private Address address;
    private LocalDateTime joinDate;

    @OneToMany(mappedBy = "user")
    private List<PicPlace> picPlaces = new ArrayList<>();
}
