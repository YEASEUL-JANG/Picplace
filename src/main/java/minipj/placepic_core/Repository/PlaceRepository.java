package minipj.placepic_core.Repository;

import minipj.placepic_core.Entity.Place;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PlaceRepository {
    @PersistenceContext
    private EntityManager em;

    public Long save(Place place){
        em.persist(place);
        return place.getId();
    }
    public Place find(Long id){
        return em.find(Place.class,id);
    }
}
