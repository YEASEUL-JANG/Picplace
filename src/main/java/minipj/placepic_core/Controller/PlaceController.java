package minipj.placepic_core.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Entity.*;
import minipj.placepic_core.Repository.PlaceRepository;
import minipj.placepic_core.Repository.PlaceSearch;
import minipj.placepic_core.Service.AuthenticationService;
import minipj.placepic_core.Service.PlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "장소등록 테스트")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/place")
public class PlaceController {

    private final PlaceService placeService;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @ApiOperation(value="장소등록",notes = "@RequestBody를 활용한 장소등록 Post Method")
    @PostMapping("/addplace")
    public ResponseEntity<?> add_place(@RequestBody PlaceForm form){
        logger.info("[add_place] 장소등록 진행, placeForm : {}",form.toString());
        Long placeId = placeService.createPlace(form);

        return new ResponseEntity<>(placeId, HttpStatus.CREATED);
    }

    @ApiOperation(value="장소목록",notes = "@RequestBody를 활용한 장소등록 Get Method")
    @PostMapping("/placelist")
    public ResponseEntity<?> placeList(@RequestBody PlaceSearch placeSearch){
        logger.info("[placelist] 장소목록 조회");
        logger.info("[placelist] 검색 요청 정보 : {}", placeSearch.toString());
        return new ResponseEntity<>(placeService.findPlaces(placeSearch),HttpStatus.OK);
    }

    @ApiOperation(value="상세장소",notes = "@RequestBody를 활용한 장소등록 Get Method")
    @PostMapping("/placedetail")
    public ResponseEntity<?> placeList(@RequestBody Long placeId){
        logger.info("[placelist] 장소 상세 조회");
        return new ResponseEntity<>(placeService.findAPlace(placeId),HttpStatus.OK);
    }



}
