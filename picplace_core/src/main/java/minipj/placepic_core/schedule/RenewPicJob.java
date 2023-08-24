package minipj.placepic_core.schedule;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minipj.placepic_core.Entity.PicPlace;
import minipj.placepic_core.Entity.User;
import minipj.placepic_core.Service.PlaceService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@Slf4j
@PersistJobDataAfterExecution //작업실행 후 해당 작업의 jobdatamap을 유지
@DisallowConcurrentExecution //동시 작업실행 방지
@NoArgsConstructor
public class RenewPicJob implements Job {

    @Autowired
    private  PlaceService placeService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("[RenewPicJob] Job Executed");
        List<PicPlace> allPicPlaces = placeService.findAllPicPlaces();
        // 현재 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        for(PicPlace p : allPicPlaces){
            LocalDateTime picDate = p.getPicDate();
            long datsBetween = ChronoUnit.DAYS.between(picDate,now);
            if(datsBetween > 7){
                placeService.deletePlacePic(p.getUser().getUserId(),p.getPlace().getPlaceId());
            }
            //long secondsBetween = ChronoUnit.SECONDS.between(picDate, now);
            log.info("[RenewPicJob Executed] 시간차 : {}",datsBetween);
//            if (secondsBetween > 10) {
//                log.info("[RenewPicJob Executed] 만료된 placePic을 삭제합니다. placeid : {}, userId: {}",p.getPlace().getPlaceId(),p.getUser().getUserId());
//                placeService.deletePlacePic(p.getUser().getUserId(),p.getPlace().getPlaceId());
//            }
        }
    }

}
