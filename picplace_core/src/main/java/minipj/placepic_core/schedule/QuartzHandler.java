package minipj.placepic_core.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class QuartzHandler {

    private final Scheduler scheduler;

    public <T extends Job> void addJob(Class<? extends Job> job, String name, String desc, Map params,String cron) throws SchedulerException{
        log.info("[addJob] jobDetail 객체 생성");
        JobDetail jobDetail = buildJobDetail(job, name, desc, params);
        log.info("[addJob] job 실행조건 trigger 생성");
        Trigger trigger = buildCronTrigger(cron);
        log.info("[addJob] job 중복체크");
        if(scheduler.checkExists(jobDetail.getKey())) scheduler.deleteJob(jobDetail.getKey());
        log.info("[addJob] job 생성");
        scheduler.scheduleJob(jobDetail, trigger);
    }
    //Trigger 설정 (job의 실행조건)
    private Trigger buildCronTrigger(String cronExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
                .build();
    }

    //Job의 세부설정(jobDetail) 반환
    public <T extends Job> JobDetail buildJobDetail(Class<? extends Job> job, String name, String desc, Map params) {
        //jobDetail에 연결될 데이터 설정객체
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        return JobBuilder
                .newJob(job)
                .withIdentity(name)
                .withDescription(desc)
                .usingJobData(jobDataMap)
                .build();
    }

    public <T extends Job> void deleteJob(String name) throws SchedulerException {
        JobKey jobKey = new JobKey(name);
        if(scheduler.checkExists(jobKey)) scheduler.deleteJob(jobKey);
    }
}
