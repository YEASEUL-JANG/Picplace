package minipj.placepic_core.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minipj.placepic_core.Controller.PlaceForm;
import minipj.placepic_core.Entity.Address;
import minipj.placepic_core.Entity.Place;
import minipj.placepic_core.Repository.PlaceRepository;
import minipj.placepic_core.Repository.PlaceSearch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;


    @Transactional
    public Long createPlace(PlaceForm form) {
        log.info("[createPlace] place 등록시작");
        log.info("[createPlace] Address객체 생성 ");
        Address address = new Address(form.getAddress(),form.getDetailAddress(), form.getZipcode());
        log.info("[createPlace] Place 객체에 정보 저장");
        Place place = Place.createPlace(form.getName(), form.getStartTime(),
                form.getEndTime(), form.getContent(),
                address, form.getPlaceType(),
                 form.getPlacePhotos(), form.getMenuList());
        log.info("[createPlace] place 등록 진행");
        Long placeId = placeRepository.save(place);
        log.info("[createPlace] place 등록 완료");

        return placeId;
    }

    public List<PlaceForm> findPlaces(PlaceSearch placeSearch) {
    log.info("[findAllPlace] Place 목록 출력");
        return placeRepository.findPlaces(placeSearch);
    }

    public PlaceForm findAPlace(Long placeId) {
        log.info("[findAPlace] Place 상세정보 출력");
        return placeRepository.findAPlace(placeId);
    }
}
