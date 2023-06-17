package minipj.placepic_core.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long placePhotoId;

    @Column(name="place_image")
    private String placeImage;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;



}
