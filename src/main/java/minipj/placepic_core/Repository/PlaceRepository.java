package minipj.placepic_core.Repository;

import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Entity.Place;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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
    public List<Place> findAll()
}
