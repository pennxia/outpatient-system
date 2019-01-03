package oss;

import cn.nobitastudio.oss.scheduler.job.EatDrugRemindJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.nobitastudio.oss.OSSApplication;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
public class JunitTest {

    @Test
    public void show() throws SchedulerException, InterruptedException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        scheduler.start();
        Trigger trigger = TriggerBuilder.newTrigger() // 使用TriggerBuilder创建Trigger
                .withIdentity("trigger1", "group1")
                .startAt(new Date(LocalDateTime.of(2019,1,3,12,52,59).minusSeconds(10).toInstant(ZoneOffset.of("+8")).toEpochMilli()))
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()) // 使用SimpleScheduleBuilder创建simpleSchedule) // 最多执行100次,此处需要注意，不包括第一次执行的
                .build();
        JobDetail detail = JobBuilder.newJob(EatDrugRemindJob.class).build();
        System.out.println("开始调度，" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        scheduler.scheduleJob(detail,trigger);
        Thread.sleep(5000000);

        //System.out.println(LocalDateTime.now().plusSeconds(20).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        // 时间戳 localdatetime 互转
//        Long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
//        LocalDateTime time2 =LocalDateTime.ofEpochSecond(timestamp/1000,0,ZoneOffset.ofHours(8));
    }
}
