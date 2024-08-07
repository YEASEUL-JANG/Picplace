package minipj.placepic_core.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Entity.*;
import minipj.placepic_core.Entity.Form.PlaceForm;
import minipj.placepic_core.Repository.PlaceSearch;
import minipj.placepic_core.Service.AuthenticationService;
import minipj.placepic_core.Service.PlaceService;
import minipj.placepic_core.response.ApiResponse;
import minipj.placepic_core.response.ErrorCode;
import minipj.placepic_core.response.GeneralException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ApiResponse add_place(@RequestBody PlaceForm form){
        logger.info("[add_place] 장소등록 진행, placeForm : {}",form.toString());
        Long placeId = placeService.createPlace(form);

        return ApiResponse.success(placeId);
    }
//    사진 업로드
@ApiOperation(value="이미지업로드",notes = "@RequestParam 활용한 이미지 업로드 Post Method")
@PostMapping("/uploadImage")
public ApiResponse uploadImage(@RequestParam("menuImages") List<MultipartFile> menuImages,
                               @RequestParam("placeImages")List<MultipartFile> placeImages) throws IOException {
    long maxFileSize = 5 * 1024 * 1024; // 5MB
    List<String> allowedMimeTypes = List.of("image/jpeg", "image/png");
     for(MultipartFile menuImage : menuImages) {
         logger.info("[uploadImage] 메뉴 이미지업로드 시작, menuImage : {}",menuImage.getOriginalFilename());
         logger.info("[uploadImage] 메뉴 이미지 파일 크기 검증 시작, menuImage : {}",menuImage.getOriginalFilename());
         if (menuImage.getSize() > maxFileSize) {
             throw new GeneralException(ErrorCode.MAXIMUM_SIZE_FAIL);
         }
         logger.info("[uploadImage] 메뉴 이미지 파일 유형 검증 시작, menuImage : {}",menuImage.getOriginalFilename());
         String contentType = menuImage.getContentType();
         if (contentType == null || !allowedMimeTypes.contains(contentType)) {
             throw new GeneralException(ErrorCode.INVALID_UPLOAD_TYPE);
         }
         String menuName = placeService.uploadMenuImage(menuImage);
         if(menuName == ""|| menuName == null){
             throw new GeneralException(ErrorCode.UPLOAD_FAIL);
         }
         logger.info("[uploadImage] 메뉴 이미지업로드 완료, menuImage : {}",menuImage.getOriginalFilename());
     }
    for(MultipartFile placeImage : placeImages) {
        logger.info("[uploadImage] 매장 이미지업로드 시작, placeImage : {}",placeImage.getOriginalFilename());
        logger.info("[uploadImage] 매장 이미지파일 크기 검증 시작, placeImage : {}",placeImage.getOriginalFilename());
        if (placeImage.getSize() > maxFileSize) {
            throw new GeneralException(ErrorCode.MAXIMUM_SIZE_FAIL);
        }

        logger.info("[uploadImage] 매장 이미지파일 유형 검증 시작, placeImage : {}",placeImage.getOriginalFilename());
        String contentType = placeImage.getContentType();
        if (contentType == null || !allowedMimeTypes.contains(contentType)) {
            throw new GeneralException(ErrorCode.INVALID_UPLOAD_TYPE);
        }

        String placeName = placeService.uploadPlaceImage(placeImage);
        if(placeName == ""|| placeName == null){
            throw new GeneralException(ErrorCode.UPLOAD_FAIL);
        }
        logger.info("[uploadImage] 매장 이미지업로드 완료, placeImage : {}",placeImage.getOriginalFilename());
    }
    return ApiResponse.success(menuImages.size()+placeImages.size());
}


    @ApiOperation(value="장소목록",notes = "@RequestBody를 활용한 장소등록 Get Method")
    @PostMapping("/placelist")
    public ApiResponse<List<PlaceForm>> placeList(@RequestBody(required = false) PlaceSearch placeSearch){
        logger.info("[placelist] 장소목록 조회");
        logger.info("[placelist] 검색 요청 정보 : {}", placeSearch.toString());
        return ApiResponse.success(placeService.findPlaces(placeSearch));
    }

    @ApiOperation(value="상세장소",notes = "@RequestBody를 활용한 장소조회 Get Method")
    @GetMapping("/placedetail/{placeId}")
    public ApiResponse placeList(@PathVariable Long placeId){
        logger.info("[placelist] 장소 상세 조회");
        return ApiResponse.success(placeService.findAPlace(placeId));
    }

    @ApiOperation(value="장소삭제",notes = "@RequestBody를 활용한 장소삭제 Get Method")
    @PostMapping("/placedelete/{placeId}")
    public ApiResponse placeDelete(@PathVariable Long placeId,@RequestBody Map<String, Long> requestBody){
        logger.info("[placelist] 장소 삭제 전 placephoto 삭제진행");
        Long userId = requestBody.get("userId");
        placeService.deletePlacePic(userId,placeId);
        logger.info("[placelist] 장소 삭제 전 menu 삭제진행");
        placeService.deleteMenu(placeId);
        logger.info("[placelist] 장소 삭제 전 placePhoto 삭제진행");
        placeService.deletePlacePhoto(placeId);
        logger.info("[placelist] 장소 삭제 진행");
        placeService.deletePlace(placeId);
        return ApiResponse.success();
    }

    @ApiOperation(value="장소수정",notes = "@PathVariable를 활용한 장소수정 Post Method")
    @PutMapping("/placeEdit/{placeId}")
    public ApiResponse placeEdit(@RequestBody PlaceForm form){
        logger.info("[add_place] 장소수정 진행, placeForm : {}",form.toString());
        Long editplaceId = placeService.editPlace(form);
        return ApiResponse.success(editplaceId);
    }

    @ApiOperation(value="찜하기",notes = "@PathVariable를 활용한 장소찜하기")
    @PostMapping("/placePic/{placeId}")
    public ApiResponse placePic( @PathVariable Long placeId, @RequestBody Map<String, Long> requestBody){
        Long userId = requestBody.get("userId");
        logger.info("[place_pic] 장소찜하기 , placeId : {}, userId : {}", placeId, userId);
        Long placepicId = placeService.placePic(placeId, userId);
        return ApiResponse.success(placepicId);
    }

    @ApiOperation(value="찜목록",notes = "@PathVariable를 활용한 장소찜목록")
    @PostMapping("/placePicList/{userId}")
    public ApiResponse placePicList( @PathVariable Long userId){
        logger.info("[place_pic] 장소찜목록 ,userId : {}", userId);
        return ApiResponse.success(placeService.findPicPlaces(userId));
    }
    @ApiOperation(value="전체찜목록",notes = "@Quartz를 활용한 전체 찜장소목록")
    @PostMapping("/placePicList")
    public List<PicPlace> placePicListAll(){
        return placeService.findAllPicPlaces();
    }

    @ApiOperation(value="찜삭제",notes = "@PathVariable를 활용한 장소찜 삭제")
    @PostMapping("/placePic/delete/{placeId}")
    public ApiResponse placePicDelete( @PathVariable Long placeId,@RequestBody Map<String, Long> requestBody){
        Long userId = requestBody.get("userId");
        logger.info("[place_pic] 장소찜 삭제 ,userId : {}, placeId: {}", userId,placeId);
        placeService.deletePlacePic(userId,placeId);
        return ApiResponse.success();
    }
}
