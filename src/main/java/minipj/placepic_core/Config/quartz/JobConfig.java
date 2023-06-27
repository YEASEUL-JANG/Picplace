package minipj.placepic_core.Config.quartz;

import lombok.RequiredArgsConstructor;
import minipj.placepic_core.schedule.QuartzHandler;
import minipj.placepic_core.schedule.RenewPicJob;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    //쿼트 스케줄 객체
//    private final Scheduler scheduler;

    private final QuartzHandler quartzHandler;
    @PostConstruct//클래스가 인스턴스화 되자마자 자동으로 동작
    public void run() {
//        JobDetail detail = runJobDetail(TestJob.class, new HashMap<>());
        Map map = new HashMap<>();
        map.put("dateTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        try{//크론형식 지정 후 스케줄 시작
            quartzHandler.addJob(RenewPicJob.class,"PlcPlaceJob","오후 12시마다 7일 이상된 찜목록을 삭제함.",map,"0 0 12 * * ?");
        }catch (SchedulerException e){
            e.printStackTrace();
        }
    }
//    public Trigger runJobTrigger(String scheduleExp){
//        //크론 스케줄 사용
//        return TriggerBuilder.newTrigger()
//                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
//    }
//    public JobDetail runJobDetail(Class job, Map params){
//        JobDataMap jobDataMap = new JobDataMap();
//        jobDataMap.putAll(params);
//        //스케줄 생성
//        return newJob(job).usingJobData(jobDataMap).build();
//    }
}
