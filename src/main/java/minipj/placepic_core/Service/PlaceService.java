package minipj.placepic_core.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minipj.placepic_core.Controller.PlaceForm;
import minipj.placepic_core.Entity.Address;
import minipj.placepic_core.Entity.Place;
import minipj.placepic_core.Entity.User;
import minipj.placepic_core.Repository.PlaceRepository;
import minipj.placepic_core.Repository.PlaceSearch;
import minipj.placepic_core.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

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
                address,form.getLat(),form.getLng(), form.getPlaceType(),form.getMenuType(),
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
    public String uploadMenuImage(MultipartFile menuImage) throws IOException {

        //본래 파일이름
        String origName = menuImage.getOriginalFilename();
        //원래는 uuid로 파일이름을 변경하지만..파일객체를 따로 저장하지 않을것이므로
        //고유 파일명으로 저장한다.
        //String uuid = UUID.randomUUID().toString();
        //불러올 파일 경로
        String savedPath = fileDir + origName;
        //로컬에 저장
        menuImage.transferTo(new File(savedPath));
        return origName;
    }

    @Transactional
    public String uploadPlaceImage(MultipartFile placeImage) throws IOException {
        String origName = placeImage.getOriginalFilename();
        String savedPath = fileDir + origName;
        placeImage.transferTo(new File(savedPath));
        return origName;
    }

    @Transactional
    public Long placePic(Long placeId, Long userId) {
        log.info("[placePic] 찜장소 중복확인");
        if(placeRepository.checkduplPic(placeId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"이미 찜한 장소입니다.");
        }
        log.info("[placePic] 찜장소 등록 시작");
        Optional<User> picuser = userRepository.findById(userId);
        return placeRepository.placePic(placeId,picuser.get());
    }

    @Transactional
    public void deletePlacePic(Long userId, Long placeId) {
        placeRepository.deletePlacePic(userId,placeId);
    }

    public List<PlaceForm> findPicPlaces(Long userId) {
        log.info("[placePic] 찜목록 조회");
        return placeRepository.findPicPlaces(userId);
    }
}
