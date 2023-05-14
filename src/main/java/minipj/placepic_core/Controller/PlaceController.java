package minipj.placepic_core.Controller;

import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Entity.Place;
import minipj.placepic_core.Repository.PlaceRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceRepository placeRepository;

    @PostMapping("/addplace")
    public void add_place(@RequestBody Place place){

    }
}
