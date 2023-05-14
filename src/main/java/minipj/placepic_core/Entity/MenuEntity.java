package minipj.placepic_core.Entity;

import javax.persistence.*;

@Entity
public class MenuEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="menulist_id")
    private Long id;
    @Embedded
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_id")
    private Place place;

}
