package minipj.placepic_core.Repository;

import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Entity.Place;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PlaceRepository {
    private final EntityManager em;

    public Long save(Place place){
        em.persist(place);
        return place.getId();
    }
    public Place find(Long id){
        return em.find(Place.class,id);
    }
}
