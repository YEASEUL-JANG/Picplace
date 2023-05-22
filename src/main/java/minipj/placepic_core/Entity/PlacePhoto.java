package minipj.placepic_core.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class PlacePhoto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="place_photo_id")
    private Long id;

    @Column(name="place_image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;
}
