package minipj.placepic_core.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minipj.placepic_core.Controller.PlaceForm;
import minipj.placepic_core.Entity.Address;
import minipj.placepic_core.Entity.Place;
import minipj.placepic_core.Repository.PlaceRepository;
import minipj.placepic_core.Repository.PlaceSearch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Value("${file.dir}")
    private String fileDir;

    @Transactional //commit을 하면서 영속성
    public Long createPlace(PlaceForm form) {
        log.info("[createPlace] place 등록시작");
        log.info("[createPlace] Address객체 생성 ");
        Address address = new Address(form.getAddress(),form.getDetailAddress(), form.getZipcode());
        log.info("[createPlace] Place 객체에 정보 저장");
        Place place = Place.createPlace(form.getName(), form.getStartTime(),
                form.getEndTime(), form.getContent(),
                address, form.getPlaceType(),form.getMenuType(),
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

    @Transactional
    public void deletePlace(Long placeId) {
        log.info("[deletePlace] 삭제 장소Id : {}",placeId);
        placeRepository.deletePlace(placeId);
    }

    @Transactional
    public Long editPlace(PlaceForm form) {
        log.info("[deletePlace] 수정진행");
        return placeRepository.editPlace(form);
    }
    @Transactional
    public void uploadMenuImage(MultipartFile menuImage) throws IOException {

        //본래 파일이름
        String origName = menuImage.getOriginalFilename();
        //원래는 uuid로 파일이름을 변경하지만..파일객체를 따로 저장하지 않을것이므로
        //고유 파일명으로 저장한다.
        //String uuid = UUID.randomUUID().toString();
        //불러올 파일 경로
        String savedPath = fileDir + origName;
        //로컬에 저장
        menuImage.transferTo(new File(savedPath));

    }

    @Transactional
    public void uploadPlaceImage(MultipartFile placeImage) throws IOException {
        String origName = placeImage.getOriginalFilename();
        String savedPath = fileDir + origName;
        placeImage.transferTo(new File(savedPath));
    }
}
