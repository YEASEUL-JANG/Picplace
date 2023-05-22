package minipj.placepic_core.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Entity.Menu;
import minipj.placepic_core.Entity.Place;
import minipj.placepic_core.Entity.PlacePhoto;
import minipj.placepic_core.Repository.PlaceRepository;
import minipj.placepic_core.Service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "장소등록 테스트")
@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("api/place")
public class PlaceController {

    private final PlaceRepository placeRepository;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Transactional
    @ApiOperation(value="장소등록",notes = "@RequestBody를 활용한 장소등록 Post Method")
    @PostMapping("/addplace")
    public ResponseEntity<?> add_place(@RequestBody Place place){
        logger.info("[add_place] 장소등록 진행, place : {}",place.toString());
        Long placeId = placeRepository.save(place);
        return new ResponseEntity<>(placeId, HttpStatus.CREATED);
    }
}
